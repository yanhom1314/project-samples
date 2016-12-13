package controllers

import org.apache.shiro.SecurityUtils
import play.api.i18n.{I18nSupport, Messages}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

trait Secured extends Controller with I18nSupport {
  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))

  def Name(request: RequestHeader) = if (SecurityUtils.getSubject.isAuthenticated) Some(SecurityUtils.getSubject.getPrincipal.toString) else None

  def User(request: RequestHeader) = if (SecurityUtils.getSubject.isAuthenticated) Some(SecurityUtils.getSubject) else None

  def OnAuthorize(onAuthorized: Request[AnyContent] => Result)(onUnauthorized: Request[AnyContent] => (Option[SecureProfile], Result)) = Action {
    implicit request =>
      Name(request) match {
        case Some(_) => onAuthorized(request)
        case None => onUnauthorized(request) match {
          case (None, r) => r
          case (Some(_), r) => r
        }
      }
  }

  def IsAuthenticated(f: => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(_ => f)
  }

  def IsAuthenticated(f: Request[AnyContent] => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(implicit request => f(request))
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: Request[A] => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(parser)(implicit request => f(request))
  }

  def IsRole(f: => Result)(members: String*) = Security.Authenticated(User, unauthorized) {
    subject => if (members.exists(subject.hasRole(_))) Action(_ => f) else Action(Results.Forbidden)
  }

  def IsRole(f: Request[AnyContent] => Result)(members: String*) = Security.Authenticated(User, unauthorized) {
    subject => if (members.exists(subject.hasRole(_))) Action(request => f(request)) else Action(Results.Forbidden)
  }

  def IsRole[A](parser: BodyParser[A])(f: Request[A] => Result)(members: String*) = Action(parser) {
    implicit request =>
      if (members.exists(SecurityUtils.getSubject.hasRole(_))) f(request) else Results.Forbidden
  }
}

case class SecureProfile(name: String, roles: Iterable[String] = Seq[String]())

object SecureProfile {
  implicit val format: Format[SecureProfile] = Json.format[SecureProfile]
}
