package controllers

import play.api.mvc._

class HomeController extends Controller {

  implicit val myCharset = Codec.javaSupported("utf-8")

  def home(id: Long = 1L, name: String = "HHH") = Action { implicit request =>
    Redirect(routes.HomeController.index(Some(s"id:${id} name:${name}"))).withSession(request.session + ("home" -> "Ok")).flashing(request.flash + ("home" -> "FLASH HOME"))
  }

  def index(id: Option[String]) = Action { implicit request =>

    id match {
      case Some(s) => println(s"id:${s}")
      case None => println("id:_")
    }

    Redirect(routes.PersonController.index).withSession(request.session + ("index" -> "Ok")).flashing(request.flash + ("index" -> "FLASH INDEX"))
  }
}
