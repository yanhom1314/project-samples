package handlers

import javax.inject.Inject

import play.api.http._
import play.api.mvc._
import play.api.routing.Router

class SimpleHttpRequestHandler @Inject()(router: Router) extends HttpRequestHandler {
  def handlerForRequest(request: RequestHeader) = {
    try {
      println(1)
      router.routes.lift(request) match {
        case Some(handler) => (request, handler)
        case None => (request, Action(Results.NotFound))
      }
    }
    catch {
      case e: Exception =>
        sys.error(e.getLocalizedMessage)
        e.printStackTrace()
        (request, Action(Results.Unauthorized))
    }
  }
}