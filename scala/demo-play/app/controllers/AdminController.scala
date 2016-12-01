package controllers

import javax.inject.Inject

import org.apache.shiro.realm.Realm
import play.api.mvc.Action

/**
  * Created by YaFengLi on 2016/12/1.
  */
class AdminController @Inject()(realm: Realm) extends Secure {
  def sayHi(name: String) = Action {
    Ok(s"<h1>Hello ${name}!</h1>")
  }
}
