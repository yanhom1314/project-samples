package controllers

import java.util.Locale

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc._

trait CookieLang extends InjectedController with I18nSupport {

  val LANG = "lang"

  val localeForm = Form(LANG -> nonEmptyText)
  //val COUNTRY = "country"
  //val localeForm = Form(tuple(LANG -> nonEmptyText, COUNTRY -> nonEmptyText))

  def changeLocale = Action {
    implicit request =>
      localeForm.bindFromRequest.fold(e => BadRequest(""),
        lg => Redirect(request.headers.get(REFERER).getOrElse(routes.HomeController.default().url)).withCookies(Cookie(LANG, lg)).withLang(Lang(lg)))
  }

  implicit def findLang(implicit request: RequestHeader): Lang = {
    request.cookies.get(LANG) match {
      case None => Lang(Locale.getDefault)
      case Some(cookie) => Lang(cookie.value)
    }
  }
}