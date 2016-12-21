package controllers

import java.text.SimpleDateFormat

import org.apache.shiro.SecurityUtils
import play.api.Logger
import play.api.i18n.{I18nSupport, Messages}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

trait Secured extends Controller with I18nSupport {

  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))

  def Name(request: RequestHeader) = User(request) match {
    case Some(u) => Some(u.getPrincipal.toString)
    case None => None
  }

  def User(request: RequestHeader) = try {
    val subject = SecurityUtils.getSubject
    val session = subject.getSession
    session.touch()
    if (Logger.isErrorEnabled) {
      val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      println(s"id:${session.getId} start:${fmt.format(session.getStartTimestamp)} time:${fmt.format(session.getLastAccessTime)}")
    }
    if (subject.isAuthenticated) {
      Some(subject)
    } else None
  } catch {
    case _: Exception => None
  }

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

  def IsRole(members: String*)(f: => Result) = Security.Authenticated(User, unauthorized) {
    subject =>
      try {
        if (members.exists(subject.hasRole(_))) Action(f) else Action(Results.Forbidden)
      } catch {
        case _: Exception => Action(unauthorized(_))
      }
  }

  def HasRole(members: String*)(f: Request[AnyContent] => Result) = Security.Authenticated(User, unauthorized) {
    subject =>
      try {
        if (members.exists(subject.hasRole(_))) Action(implicit request => f(request)) else Action(Results.Forbidden)
      } catch {
        case _: Exception => Action(implicit request => unauthorized(request))
      }
  }

  def IsRole[A](parser: BodyParser[A], members: String*)(f: => Result) = Action(parser) {
    implicit request =>
      try {
        if (members.exists(SecurityUtils.getSubject.hasRole(_))) f else Results.Forbidden
      } catch {
        case _: Exception => unauthorized(request)
      }
  }

  def HasRole[A](parser: BodyParser[A], members: String*)(f: Request[A] => Result) = Action(parser) {
    implicit request =>
      try {
        if (members.exists(SecurityUtils.getSubject.hasRole(_))) f(request) else Results.Forbidden
      } catch {
        case _: Exception => unauthorized(request)
      }
  }
}

case class SecureProfile(name: String, roles: Iterable[String] = Seq[String]())

object SecureProfile {
  implicit val format: Format[SecureProfile] = Json.format[SecureProfile]
}
