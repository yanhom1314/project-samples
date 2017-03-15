package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.Action
import security.{Secured, SecuredProfile}
import shiro.ShiroSubjectCache

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi, val secureData: ShiroSubjectCache) extends Secured {

  def admin(name: String) = IsAuthenticated {
    implicit request =>
      User(request).fold(Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.timeout"))) { o => Ok(views.html.admin.index(o.getPrincipal.toString, o.hasRole("ROLE_ADMIN").toString)) }
  }

  def isCheck(name: String) = IsRole(name) { _ =>
    Ok(s"<h1>ROLE:${name} is OK.</h1>")
  }

  def hasCheck(name: String) = HasRole(name) { implicit request =>
    Ok(s"<h1>Hello ROLE:[${name}]</h1>")
  }

  def checkIs(name: String) = IsRole(parse.json, name) { implicit request =>
    val profile = SecuredProfile(Name(request).get, Seq(name))
    Ok(Json.toJson(profile))
  }

  def checkHas(name: String) = HasRole(parse.json, name) { implicit request =>
    val profile = SecuredProfile(Name(request).get, Seq(name))
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
