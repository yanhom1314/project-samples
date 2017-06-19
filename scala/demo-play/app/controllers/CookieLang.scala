package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, Controller, Cookie, RequestHeader}

trait CookieLang extends Controller with I18nSupport {
  val LANG = "lang"

  val localeForm = Form(LANG -> nonEmptyText)
  //val COUNTRY = "country"
  //val localeForm = Form(tuple(LANG -> nonEmptyText, COUNTRY -> nonEmptyText))

  def changeLocale = Action {
    implicit request =>
      localeForm.bindFromRequest.fold(e => BadRequest(e.errors.map(r => messagesApi(r.message, r.args)).mkString("|")),
        lg => Redirect(request.headers.get(REFERER).getOrElse(routes.HomeController.default().url)).withCookies(Cookie(LANG, lg)).withLang(Lang(lg)))
  }

  implicit def lang(implicit request: RequestHeader) = {
    request.cookies.get(LANG) match {
      case None =>
      case Some(cookie) => Lang(cookie.value)
    }
  }
}