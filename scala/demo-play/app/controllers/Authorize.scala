package controllers

import javax.inject.Inject

import models.Login
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc._
import org.apache.shiro.session.UnknownSessionException
import org.apache.shiro.subject.Subject
import play.api.Configuration
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc._

class Authorize @Inject()(conf: Configuration, val messagesApi: MessagesApi) extends Secured with CookieLang {
  val SESSION_LOGIN_NAME = "s_login_name"
  val SESSION_LOGIN_ROLE = "s_role_name"

  val loginForm = Form[Login](
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "captcha" -> text
    )(Login.apply)(Login.unapply)
  )

  def login = Action {
    implicit request =>
      Name(request) match {
        case Some(username) => Redirect(routes.Authorize.home()).withSession(SESSION_LOGIN_NAME -> username, SESSION_LOGIN_ROLE -> username)
        case None => Ok(views.html.login(loginForm))
      }
  }

  def authenticate = Action {
    implicit request => {
      loginForm.bindFromRequest().fold(
        errors => BadRequest(views.html.login(errors)),
        user => {
          var cu: Subject = null
          try {
            val username = user.username
            val password = user.password
            val remember = true
            val token = new UsernamePasswordToken(username, password)
            token.setRememberMe(remember)

            cu = SecurityUtils.getSubject
            cu.login(token)
            Redirect(routes.Authorize.home()).withSession(SESSION_LOGIN_NAME -> username, SESSION_LOGIN_ROLE -> username)
          } catch {
            case _: UnknownSessionException =>
              try {
                cu.logout()
              } catch {
                case e1: Exception => e1.printStackTrace()
              }
              Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout"))
            case _: UnknownAccountException => Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.1"))
            case _: IncorrectCredentialsException => Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.2"))
            case _: LockedAccountException => Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.3"))
            case _: AuthenticationException => Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.4"))
          }
        }
      )
    }
  }

  def logout = Action { implicit request =>
    User(request).foreach(obj => obj.logout())
    Redirect(routes.Authorize.login()).withNewSession.flashing("success" -> Messages("logout.message"))
  }

  def home = IsAuthenticated {
    implicit request => Ok(s"hello ${Name(request)}")
  }
}
