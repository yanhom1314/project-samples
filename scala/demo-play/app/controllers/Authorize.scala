package controllers

import javax.inject.Inject

import models.Login
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc._
import org.apache.shiro.subject.Subject
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.Messages
import play.api.mvc.{MessagesControllerComponents, MessagesRequest, Request}
import play.filters.csrf.{CSRFAddToken, CSRFCheck}
import security.{Secured, SecuredProfile}
import shiro.ShiroSubjectCache

class Authorize @Inject()(val secureData: ShiroSubjectCache, addToken: CSRFAddToken, checkToken: CSRFCheck, cc: MessagesControllerComponents) extends Secured(cc) {
  val loginForm = Form[Login](
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "captcha" -> text
    )(Login.apply)(Login.unapply)
  )

  def login = Action { implicit request: MessagesRequest[_] =>
    Name(request) match {
      case Some(_) => Redirect(routes.AdminController.index())
      case None => Ok(views.html.login(loginForm))
    }
  }

  def authenticate = Action { implicit request: MessagesRequest[_] =>
    loginForm.bindFromRequest().fold(
      errors => BadRequest(views.html.login(errors)),
      user => {
        var cu: Subject = null
        try {
          val username = user.username
          val password = user.password
          System.out.println(s"username:${username} password:${password}")
          val remember = true
          val token = new UsernamePasswordToken(username, password)
          token.setRememberMe(remember)

          cu = SecurityUtils.getSubject
          println(1)
          cu.login(token)
          println(2)
          secureData.save(username, cu)
          println(3)
          Ok(views.html.admin.index(username, username)).withSession(request.session + (SecuredProfile.S_USERNAME -> username))
          //Redirect(routes.AdminController.index).withSession(request.session + SecuredProfile.S_USERNAME -> username)
        } catch {
          case _: Exception => Redirect(routes.Authorize.login()).flashing("error" -> request.messages("unauthorized.message"))
        }
      }
    )
  }

  def logout = Action { implicit request: MessagesRequest[_] =>
    Name(request).foreach(un => secureData.logout(un))
    Redirect(routes.Authorize.login()).withNewSession.flashing("success" -> request.messages("logout.message"))
  }
}
