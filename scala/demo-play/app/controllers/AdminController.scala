package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.Action
import security.{Secured, SecuredProfile}
import shiro.SubjectHashData

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi, val data: SubjectHashData) extends Secured {

  def admin(name: String) = IsAuthenticated {
    implicit request =>
      User(request).fold(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout"))) { o => Ok(views.html.admin.index(o.getPrincipal.toString, o.hasRole("ROLE_ADMIN").toString)) }
  }

  def check(name: String) = IsRole(name) {
    Ok(s"<h1>ROLE:${name} is OK.</h1>")
  }

  def check1(name: String) = HasRole(name) { implicit request =>
    println(request)
    Ok(s"<h1>Hello ROLE:[${name}]</h1>")
  }

  def check2 = IsRole(parse.json, "ROLE_ADMIN") { body =>
    val un = (body \ "un").as[String]
    val profile = SecuredProfile(un, Seq("ROLE_ADMIN"))
    Ok(Json.toJson(profile))
  }

  def check3 = HasRole(parse.json, "ROLE_USER") { implicit request =>
    val body = request.body
    val un = (body \ "un").as[String]
    val profile = SecuredProfile(un, Seq("ROLE_USER"))
    Ok(Json.toJson(profile))
  }

  def info = Action { implicit request =>
    User(request).fold(Forbidden("Nothing"))(s => Ok(views.html.info(request.session.data, s)))
  }

  def json = Action(parse.json) { implicit request =>
    val body = request.body
    val un = (body \ "un").as[String]
    Ok(s"un:${un} name:${Name(request)} body:${body}")
  }
}
