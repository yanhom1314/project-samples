package controllers

import javax.inject._

import play.api.Logger
import play.api.i18n.Langs
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, ControllerComponents, Request}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(langs: Langs, cc: ControllerComponents) extends CookieLang(cc) {

  def default() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.home("Hello World!"))
  }

  def home(id: Long = 1L, name: String = "HHH") = Action { implicit request: Request[_] =>
    Redirect(routes.HomeController.index(Some(s"id:${id} name:${name}"))).withSession(request.session + ("home" -> "Ok")).flashing(request.flash + ("home" -> "FLASH HOME"))
  }

  def index(id: Option[String]) = Action { implicit request: Request[AnyContent] =>
    id match {
      case Some(s) => Logger.info(s"id:${s}")
      case None => Logger.info("id:_")
    }

    Redirect(routes.PersonController.index).withSession(request.session + ("index" -> "Ok")).flashing(request.flash + ("error" -> "FLASH INDEX"))
  }
}