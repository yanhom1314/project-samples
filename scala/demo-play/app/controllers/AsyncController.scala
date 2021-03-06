package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.i18n.I18nSupport
import play.api.mvc._
import play.twirl.api.Html

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * This controller creates an `Action` that demonstrates how to write
  * simple asychronous code in a controller. It uses a timer to
  * asynchronously delay sending a response for 1 second.
  *
  * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
  *                    run code after a delay.
  * @param exec        We need an `ExecutionContext` to execute our
  *                    asynchronous code.
  */
@Singleton
class AsyncController @Inject()(actorSystem: ActorSystem, cc: MessagesControllerComponents)(implicit exec: ExecutionContext) extends MessagesAbstractController(cc) with I18nSupport {

  /**
    * Create an Action that returns a plain text message after a delay
    * of 1 second.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/message`.
    */
  def message = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  def sayHi = Action.async { implicit request: MessagesRequest[_] => getFutureHtml(4.second).map(r => Ok(r)) }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success("Hi!")
    }
    promise.future
  }

  private def getFutureHtml(delayTime: FiniteDuration)(implicit request: MessagesRequest[_]): Future[Html] = {
    val promise: Promise[Html] = Promise[Html]()
    val start = System.currentTimeMillis()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success(views.html.hi(s"Async is ok.start:${start} time use:${System.currentTimeMillis() - start}"))
    }
    promise.future
  }
}
