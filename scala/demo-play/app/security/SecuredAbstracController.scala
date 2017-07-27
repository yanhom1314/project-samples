package security

import controllers.routes
import org.apache.shiro.subject.Subject
import play.api.i18n.Langs
import play.api.mvc._
import security.SecuredProfile.S_USERNAME
import shiro.ShiroSubjectCache

abstract class SecuredAbstracController(cc: MessagesControllerComponents, langs: Langs) extends LangAbstractController(cc, langs) {

  def secureData: ShiroSubjectCache

  def unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> messagesApi("unauthorized.message"))

  def Role(subject: Subject, roles: String*): Boolean = roles.exists(subject.hasRole(_))

  def Name(request: RequestHeader): Option[String] = {
    println("session:" + request.session.data)
    request.session.get(S_USERNAME).filter(un => secureData.get(un).isDefined)
  }

  def User(request: RequestHeader): Option[Subject] = Name(request).flatMap { un => secureData.get(un) }

  def IsAuthenticated(f: => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action(f)
  }

  def IsAuthenticated(f: MessagesRequest[_] => Result) = Security.Authenticated(User, unauthorized) {
    _ => Action { implicit request: MessagesRequest[_] => f(request) }
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: MessagesRequest[A] => Result) = Security.Authenticated(Name, unauthorized) {
    _ => Action(parser) { implicit request: MessagesRequest[A] => f(request) }
  }

  def IsRole(members: String*)(f: MessagesRequest[AnyContent] => Result) = Action {
    implicit request: MessagesRequest[AnyContent] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def IsRole[A](parser: BodyParser[A], members: String*)(f: MessagesRequest[A] => Result) = Action(parser) {
    implicit request: MessagesRequest[A] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def HasRole(members: String*)(f: MessagesRequest[_] => Result) = Action {
    implicit request: MessagesRequest[_] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }

  def HasRole[A](parser: BodyParser[A], members: String*)(f: MessagesRequest[A] => Result) = Action(parser) {
    implicit request: MessagesRequest[A] =>
      if (User(request).exists(subject => Role(subject, members: _*))) f(request) else Results.Forbidden
  }
}




