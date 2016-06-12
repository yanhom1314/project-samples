package controllers

import javax.inject._

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DemoController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def demo() = Action { implicit request =>
    Ok(views.html.demo())
  }
}
