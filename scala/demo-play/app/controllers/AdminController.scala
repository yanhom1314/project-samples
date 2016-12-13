package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.i18n.MessagesApi

class AdminController @Inject()(realm: Realm, val messagesApi: MessagesApi) extends Secured {
  def admin(name: String) = IsAuthenticated {
    Ok(s"<h1>Hello Secure ${name}!</h1>")
  }

  def roleCheck(name: String) = IsRole {
    Ok(s"<h1>Hello ${name}:[ROLE_ADMIN]</h1>")
    Ok(s"<h1>Hello Secure ${name}!</h1>")
  }("ROLE_ADMIN")
}
