package controllers

import javax.inject.Inject

import models.Login
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc._
import org.apache.shiro.session.UnknownSessionException
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc._
import play.api.{Configuration, Logger}

/**
  * User: ya_feng_li@163.com
  * Date: 13-3-8
  * Time: 上午10:16
  */
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
      request.session.get(Secured.SESSION_LOGIN_NAME) match {
        case None => Ok(views.html.login(loginForm))
        case Some(username) => Redirect(routes.Authorize.home()).withSession(Secured.SESSION_LOGIN_NAME -> username, Secured.SESSION_LOGIN_NAME -> username)
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
          val cu = SecurityUtils.getSubject
          val username = user.username
          val password = user.password
          val remember = true
          val token = new UsernamePasswordToken(username, password)
          token.setRememberMe(remember)

          try {
            cu.login(token)
            Redirect(routes.Authorize.home()).withSession(Secured.SESSION_LOGIN_NAME -> username, Secured.SESSION_LOGIN_ROLE -> username)
          } catch {
            case e: UnknownSessionException =>
              try {
                cu.logout()
              } catch {
                case e: Exception => Logger.error(e.getMessage)
              }
              Unauthorized(views.html.login).flashing("error" -> Messages("unauthorized.message"))
            case UnknownAccountException => Unauthorized(views.html.login).flashing("error" -> Messages("unauthorized.message"))
            case IncorrectCredentialsException => Forbidden(views.html.login).flashing("error" -> Messages("unauthorized.message"))
            case LockedAccountException => Unauthorized(views.html.login).flashing("error" -> Messages("unauthorized.message"))
            case AuthenticationException => NonAuthoritativeInformation(views.html.login).flashing("error" -> Messages("unauthorized.message"))
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
