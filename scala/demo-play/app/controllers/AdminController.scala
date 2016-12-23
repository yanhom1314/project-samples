package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.Action
import security.{Secured, SecuredProfile}

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi) extends Secured {

  def admin(name: String) = IsAuthenticated {
    implicit request =>
      User(request).fold(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout"))) { o => Ok(views.html.admin.index(o.getPrincipal.toString, o.hasRole("ROLE_ADMIN").toString)) }
  }

  def check(name: String) = IsRole("ROLE_ADMIN") {
    Redirect(routes.AdminController.admin(name))
  }

  def check1(name: String) = HasRole("ROLE_ADMIN") { implicit request =>
    println(request)
    Ok(s"<h1>Hello1 ${name}:[ROLE_ADMIN]</h1>")
  }

  def check2 = IsRole(parse.json, "ROLE_ADMIN") { body =>
    val un = (body \ "un").as[String]
    val profile = SecuredProfile(un, Seq("ROLE_ADMIN "))
    Ok(Json.toJson(profile))
  }

  def check3 = HasRole(parse.json, "ROLE_ADMIN") { implicit request =>
    val body = request.body
    val un = (body \ "un").as[String]
    val profile = SecuredProfile(un, Seq("ROLE_ADMIN "))
    Ok(Json.toJson(profile))
  }

  def info = Action { implicit request =>
    Ok(views.html.info(request.session.data))
  }

  def json = Action(parse.json) { implicit request =>
    val body = request.body
    val un = (body \ "un").as[String]
    Ok(s"un:${un} name:${Name(request)} body:${body}")
  }
}
