package controllers

import play.api.libs.json.{Format, Json}
import play.api.mvc._

trait Secured {

  import Secured._

  def Unauthorized(request: RequestHeader): Result

  def Name(request: RequestHeader) = request.session.get(SESSION_LOGIN_NAME)

  def Role(request: RequestHeader) = request.session.get(SESSION_LOGIN_ROLE)

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

  def IsAuthenticated(f: => Result) = Security.Authenticated(Name, Unauthorized) {
    case _ => Action(f)
  }

  def IsAuthenticated(f: => Request[AnyContent] => Result) = Security.Authenticated(Name, Unauthorized) {
    case _ => Action(implicit request => f(request))
  }

  def IsAuthenticated[A](parser: BodyParser[A])(f: => Request[A] => Result) = Security.Authenticated(Name, Unauthorized) {
    case _ => Action(parser)(implicit request => f(request))
  }

  def IsRole(members: Array[String])(f: => Request[AnyContent] => Result) = Security.Authenticated(Role, Unauthorized) {
    roleNames =>
      if (roleNames.split(SPLIT_ROLE_CHAR).exists(members.contains(_))) Action(request => f(request)) else Action(Results.Forbidden)
  }

  def IsRole[A](members: Array[String])(parser: BodyParser[A])(f: Request[A] => Result) = Action(parser) {
    implicit request =>
      Role(request) match {
        case Some(roleNames) => if (roleNames.split(SPLIT_ROLE_CHAR).exists(members.contains(_))) f(request) else Results.Forbidden
        case None => Unauthorized(request)
      }
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
