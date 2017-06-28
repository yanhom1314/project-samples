package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.Messages
import play.api.libs.json.{Json, _}
import play.api.mvc.{AnyContent, ControllerComponents, Request}
import security.{Secured, SecuredProfile}
import shiro.ShiroSubjectCache

class AdminController @Inject()(realm: Realm, val secureData: ShiroSubjectCache, cc: ControllerComponents) extends Secured(cc) {

  val ROLE_USER = "ROLE_USER"

  def index = IsAuthenticated {
    implicit request: Request[AnyContent] =>
      User(request).fold(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout"))) { o => Ok(views.html.admin.index(o.getPrincipal.toString, o.hasRole("ROLE_ADMIN").toString)) }
  }

  def is(name: String) = IsRole(name) { _ =>
    Ok(s"<h1>ROLE:${name} is OK.</h1>")
  }

  def has(name: String) = HasRole(name) { implicit request =>
    Ok(s"<h1>Hello ROLE:[${name}]</h1>")
  }

  def checkIs = IsRole(parse.json, ROLE_USER) { implicit request =>
    println("checkIs:" + request.body)
    val profile = SecuredProfile(Name(request).get, Seq(ROLE_USER))
    Ok(Json.toJson(profile))
  }

  def checkHas = HasRole(parse.json, ROLE_USER) { implicit request =>
    println("checkHas:" + request.body)
    val profile = SecuredProfile(Name(request).get, Seq(ROLE_USER))
    Ok(Json.toJson(profile))
  }

  def info = Action { implicit request: Request[AnyContent] =>
    User(request).fold(Forbidden("Nothing"))(s => Ok(views.html.info(request.session.data, s)))
  }

  def json = Action(parse.json) { implicit request: Request[JsValue] =>
    val un = (request.body \ "un").as[String]
    Ok(s"un:${un} name:${Name(request)} body:${request.body}")
  }
}
