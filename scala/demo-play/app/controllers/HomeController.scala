package controllers

import javax.inject._

import play.api.Logger
import play.api.i18n.Langs

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(langs: Langs) extends CookieLang {

  def default() = Action { implicit request =>
    Ok(views.html.home("Hello World!"))
  }

  def home(id: Long = 1L, name: String = "HHH") = Action { implicit request =>
    Redirect(routes.HomeController.index(Some(s"id:${id} name:${name}"))).withSession(request.session + ("home" -> "Ok")).flashing(request.flash + ("home" -> "FLASH HOME"))
  }

  def index(id: Option[String]) = Action { implicit request =>
    id match {
      case Some(s) => Logger.info(s"id:${s}")
      case None => Logger.info("id:_")
    }

    Redirect(routes.PersonController.index).withSession(request.session + ("index" -> "Ok")).flashing(request.flash + ("index" -> "FLASH INDEX"))
  }
}
