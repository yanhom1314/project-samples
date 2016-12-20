package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.MessagesApi

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi) extends Secured {
  def admin(name: String) = IsAuthenticated {
    Ok(s"<h1>Hello Secure ${name}!</h1>")
  }

  def check(name: String) = IsRole("ROLE_ADMIN") {
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }

  def check1(name: String) = HasRole("ROLE_ADMIN") { implicit request =>
    println(request)
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }

  def check2(name: String) = IsRole(parse.json, "ROLE_ADMIN") {
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }

  def check3(name: String) = HasRole(parse.json, "ROLE_ADMIN") { implicit request =>
    println(request)
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
  }
}
