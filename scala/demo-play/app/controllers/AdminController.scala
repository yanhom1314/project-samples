package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.Action
import security.Secured

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi) extends Secured {
  def admin(name: String) = IsAuthenticated {
    implicit request =>
      User(request).flatMap(obj => Some(Ok(views.html.admin.index(obj.getPrincipal.toString, obj.hasRole("ROLE_ADMIN").toString))))
        .getOrElse(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout")))
  }

  def check(name: String) = IsRole("ROLE_ADMIN") {
    Redirect(routes.AdminController.admin(name))
  }

  def check1(name: String) = HasRole("ROLE_ADMIN") { implicit request =>
    User(request).flatMap(obj => Some(Ok(views.html.admin.index(obj.getPrincipal.toString, obj.getPrincipal.toString))))
      .getOrElse(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout")))
  }

  def check2(name: String) = IsRole(parse.json, "ROLE_ADMIN") {
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }

  def check3(name: String) = HasRole(parse.json, "ROLE_ADMIN") { implicit request =>
    println(request)
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }

  def info = Action { implicit request =>
    Ok(views.html.info())
  }
}
