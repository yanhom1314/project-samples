package controllers

import javax.inject.Inject

import models.Login
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc._
import org.apache.shiro.subject.Subject
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc._
import security.{Secured, SecuredProfile}
import shiro.ShiroSubjectCache

class Authorize @Inject()(val messagesApi: MessagesApi, val secureData: ShiroSubjectCache) extends Secured with CookieLang {
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
        case Some(_) => Redirect(routes.AdminController.index())
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
            secureData.save(username, cu)
            Redirect(routes.AdminController.index).withSession(SecuredProfile.S_USERNAME -> username)
          } catch {
            case _: Exception => Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))
          }
        }
      )
    }
  }

  def logout = Action { implicit request =>
    Name(request).foreach(un => secureData.logout(un))
    Redirect(routes.Authorize.login()).withNewSession.flashing("success" -> Messages("logout.message"))
  }
}
