package security

import controllers.routes
import org.apache.shiro.subject.Subject
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import shiro.SubjectHashData

trait Secured extends Controller with I18nSupport {

  import SecuredProfile._

  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))

  def Role(subject: Subject, roles: String*): Boolean = roles.exists(subject.hasRole(_))

  def Name(request: RequestHeader): Option[String] = request.session.get(S_USERNAME).filter(un => SubjectHashData.get(un).isDefined)

  def User(request: RequestHeader): Option[Subject] = Name(request).flatMap { un => SubjectHashData.get(un) }

  def IsAuthenticated(f: => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action(_ => f)
  }

  def IsAuthenticated(f: Request[AnyContent] => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action(implicit request => f(request))
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: Request[A] => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(parser)(implicit request => f(request))
  }

  def IsRole(members: String*)(f: => Result) = Security.Authenticated(User, unauthorized) {
    subject =>
      try {
        if (Role(subject, members: _*)) Action(f) else Action(Results.Forbidden)
      } catch {
        case _: Exception => Action(unauthorized(_))
      }
  }

  def HasRole(members: String*)(f: Request[AnyContent] => Result) = Security.Authenticated(User, unauthorized) {
    subject =>
      try {
        if (Role(subject, members: _*)) Action(implicit request => f(request)) else Action(Results.Forbidden)
      } catch {
        case _: Exception => Action(implicit request => unauthorized(request))
      }
  }

  def IsRole[A](parser: BodyParser[A], members: String*)(f: A => Result) = Action(parser) {
    implicit request =>
      try {
        if (User(request).exists(subject => Role(subject, members: _*))) f(request.body) else Results.Forbidden
      } catch {
        case _: Exception => unauthorized(request)
      }
  }

  def HasRole[A](parser: BodyParser[A], members: String*)(f: Request[A] => Result) = Action(parser) {
    implicit request =>
      try {
        if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
      } catch {
        case _: Exception => unauthorized(request)
      }
  }
}




