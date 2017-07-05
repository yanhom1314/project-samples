package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc._

abstract class CookieLang(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) with I18nSupport {

  val LANG = "lang"

  val localeForm = Form(LANG -> nonEmptyText)

  def changeLanguage = Action { implicit request: Request[AnyContent] =>
    localeForm.bindFromRequest.fold(e => BadRequest(e.errors.toString()),
      lg => {
        defaultLang = Lang(lg)
        Redirect(request.headers.get(REFERER).getOrElse(routes.HomeController.default().url)).withCookies(Cookie(LANG, lg)).withLang(defaultLang)
      })
  }

  implicit var defaultLang: Lang = Lang(java.util.Locale.getDefault)
}