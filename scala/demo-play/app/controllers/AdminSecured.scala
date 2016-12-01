package controllers

import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc.{Controller, RequestHeader, Result}

trait AdminSecured extends Controller with Secured with I18nSupport {
  def Unauthorized(request: RequestHeader): Result = Redirect(routes.Authorize.login()).flashing("error" -> Messages("unauthorized.message"))
}