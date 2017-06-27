package security

import controllers.routes
import org.apache.shiro.subject.Subject
import play.api.i18n.I18nSupport
import play.api.mvc._
import security.SecuredProfile.S_USERNAME
import shiro.ShiroSubjectCache

trait Secured extends InjectedController with I18nSupport {

  def secureData: ShiroSubjectCache

  //def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> messagesApi("unauthorized.message"))

  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> "NOTHING")

  def Role(subject: Subject, roles: String*): Boolean = roles.exists(subject.hasRole(_))

  def Name(request: RequestHeader): Option[String] = request.session.get(S_USERNAME).filter(un => secureData.get(un).isDefined)

  def User(request: RequestHeader): Option[Subject] = Name(request).flatMap { un => secureData.get(un) }

  def IsAuthenticated(f: => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action(f)
  }

  def IsAuthenticated(f: Request[AnyContent] => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action { implicit request: Request[AnyContent] => f(request) }
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: Request[A] => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(parser) { implicit request: Request[A] => f(request) }
  }

  def IsRole(members: String*)(f: Request[AnyContent] => Result) = Action {
    implicit request: Request[AnyContent] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def IsRole[A](parser: BodyParser[A], members: String*)(f: Request[A] => Result) = Action(parser) {
    implicit request: Request[A] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def HasRole(members: String*)(f: Request[AnyContent] => Result) = Action {
    implicit request: Request[AnyContent] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def HasRole[A](parser: BodyParser[A], members: String*)(f: Request[A] => Result) = Action(parser) {
    implicit request: Request[A] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }
}




