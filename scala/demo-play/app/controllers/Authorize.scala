package controllers

import javax.inject.Inject

import models.Login
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
class Authorize @Inject()(conf: Configuration, implicit val messagesApi: MessagesApi) extends AdminSecured with CookieLang {

  val securt = conf.getString("secure.crt")

  val loginForm = Form[Login](
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "captcha" -> nonEmptyText
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
    implicit request =>
      Redirect(routes.Authorize.home())
  }

  def authenticate = Action {
    implicit request =>
      request.session.get(Secured.SESSION_LOGIN_NAME) match {
        case None =>
          loginForm.bindFromRequest().fold(
            errors => BadRequest(views.html.login(errors)),
            user =>
              if (user.username == "admin" && securt.exists(_.equalsIgnoreCase(user.password)))
                Redirect(routes.Authorize.home()).withSession(Secured.SESSION_LOGIN_NAME -> user.username, Secured.SESSION_LOGIN_ROLE -> user.username)
              else {
                Logger.info(f"#login:${user.username} ${user.password} ${securt.get}")
                Unauthorized(request)
              }
          )
        case Some(username) => Redirect(routes.Authorize.home()).withSession(Secured.SESSION_LOGIN_NAME -> username, Secured.SESSION_LOGIN_ROLE -> username)
      }
  }

  def logout = Action {
    implicit request =>
      Redirect(routes.Authorize.login()).withNewSession.flashing("success" -> Messages("logout.message"))
  }

  def home = IsAuthenticated {
    implicit request =>
      Ok(s"hello ${Name(request)}")
  }
}
