package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, Controller, Cookie, RequestHeader}

trait CookieLang extends Controller with I18nSupport {

  val localeForm = Form("locale" -> nonEmptyText)

  def changeLocale = Action {
    implicit request =>
      val referrer = request.headers.get(REFERER).getOrElse(HOME_URL)
      localeForm.bindFromRequest.fold(
        _ => {
          BadRequest(referrer)
        },
        locale => {
          Redirect(referrer).withCookies(Cookie(LANG, locale))
        })
  }

  implicit def lang(implicit request: RequestHeader) = {
    request.cookies.get(LANG) match {
      case None =>
      case Some(cookie) => Lang(cookie.value)
    }
  }

  val LANG = "lang"
  val HOME_URL = "/"
}