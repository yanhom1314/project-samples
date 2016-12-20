package controllers

import javax.inject.Inject

import models.Login
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc._
import org.apache.shiro.session.UnknownSessionException
import org.apache.shiro.subject.Subject
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc._
import play.api.{Configuration, Logger}

class Authorize @Inject()(conf: Configuration, val messagesApi: MessagesApi) extends Secured with CookieLang {
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

  def admin = IsAuthenticated {
    Redirect(routes.Authorize.home())
  }

  def authenticate = Action {
    implicit request => {
      loginForm.bindFromRequest().fold(
        errors => BadRequest(views.html.login(errors)),
        user => {
          var cu: Subject = null
          try {
            cu = SecurityUtils.getSubject
            val username = user.username
            val password = user.password
            val remember = true
            val token = new UsernamePasswordToken(username, password)
            token.setRememberMe(remember)
            cu.login(token)
            cu.getSession.touch()
            Redirect(routes.Authorize.home()).withSession(SESSION_LOGIN_NAME -> username, SESSION_LOGIN_ROLE -> username)
          } catch {
            case e: UnknownSessionException =>
              try {
                if (cu != null) cu.logout()
              } catch {
                case e: Exception => Logger.error(e.getMessage)
              }
              Unauthorized(views.html.login(loginForm)).flashing("error" -> Messages("unauthorized.message"))
            case _: UnknownAccountException => Unauthorized(views.html.login(loginForm)).flashing("error" -> Messages("unauthorized.message"))
            case _: IncorrectCredentialsException => Forbidden(views.html.login(loginForm)).flashing("error" -> Messages("unauthorized.message"))
            case _: LockedAccountException => Unauthorized(views.html.login(loginForm)).flashing("error" -> Messages("unauthorized.message"))
            case _: AuthenticationException => NonAuthoritativeInformation(views.html.login(loginForm)).flashing("error" -> Messages("unauthorized.message"))
          }
        }
      )
    }
  }

  def logout = Action {
    Redirect(routes.Authorize.login()).withNewSession.flashing("success" -> Messages("logout.message"))
  }

  def home = IsAuthenticated {
    implicit request => Ok(s"hello ${Name(request)}")
  }
}
