package controllers

import org.apache.shiro.SecurityUtils
import play.api.i18n.{I18nSupport, Messages}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

trait Secured extends Controller with I18nSupport {

  import Secured._

  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))

  def Name(request: RequestHeader) = if (SecurityUtils.getSubject.isAuthenticated) Some(SecurityUtils.getSubject.getPrincipal.toString) else None

  def User(request: RequestHeader) = if (SecurityUtils.getSubject.isAuthenticated) Some(SecurityUtils.getSubject) else None

  def OnAuthorize(onAuthorized: Request[AnyContent] => Result)(onUnauthorized: Request[AnyContent] => (Option[SecureProfile], Result)) = Action {
    implicit request =>
      Name(request) match {
        case Some(_) => onAuthorized(request)
        case None => onUnauthorized(request) match {
          case (None, r) => r
          case (Some(u), r) => r.withSession(SESSION_LOGIN_NAME -> u.name, SESSION_LOGIN_ROLE -> u.roles.mkString(SPLIT_ROLE_CHAR))
        }
      }
  }

  def IsAuthenticated(f: => Result) = Security.Authenticated(Name, unauthorized) {
    case _ => Action(_ => f)
  }

  def IsAuthenticated(f: Request[AnyContent] => Result) = Security.Authenticated(Name, unauthorized) {
    case _ => Action(implicit request => f(request))
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: Request[A] => Result) = Security.Authenticated(Name, unauthorized) {
    case _ => Action(parser)(implicit request => f(request))
  }

  def IsRole(members: String*)(f: Request[AnyContent] => Result) = Security.Authenticated(User, unauthorized) {
    subject =>
      if (members.exists(subject.hasRole(_))) Action(request => f(request)) else Action(Results.Forbidden)
  }

  def IsRole[A](members: String*)(parser: BodyParser[A])(f: Request[A] => Result) = Action(parser) {
    implicit request =>
      if (members.exists(SecurityUtils.getSubject.hasRole(_))) f(request) else Results.Forbidden
  }
}

object Secured {
  val SPLIT_ROLE_CHAR = "\\s"
  val SESSION_LOGIN_NAME = "s_user_name"
  val SESSION_LOGIN_ROLE = "s_user_role"
}

case class SecureProfile(name: String, roles: Iterable[String] = Seq[String]())

object SecureProfile {
  implicit val format: Format[SecureProfile] = Json.format[SecureProfile]
}
