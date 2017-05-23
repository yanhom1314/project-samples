package demo.http

import java.io.File
import java.util.concurrent.TimeUnit

import akka.Done
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, get, getFromFile, onComplete, onSuccess, path, pathPrefix, post, _}
import akka.stream.Materializer
import demo.WebServer.BASE_DIR
import spray.json.DefaultJsonProtocol._

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ExecutionContextExecutor, Future}

object SprayJsonHttp {
  val duration = FiniteDuration(6, TimeUnit.SECONDS)

  val cache = mutable.Map[Long, Item]()

  final case class Item(name: String, id: Long)

  implicit val itemFormat = jsonFormat2(Item)

  def fetchItem(itemId: Long)(implicit dispatcher: ExecutionContextExecutor): Future[Option[Item]] = Future(cache.get(itemId))

  def saveOrder(itemId: Long)(implicit dispatcher: ExecutionContextExecutor): Future[Done] = Future {
    cache += itemId -> Item(itemId.toString, itemId)
    Done
  }

  def route(implicit mat: Materializer, dispatcher: ExecutionContextExecutor) = {
    path("") {
      get {
        complete {
          //Future(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          HttpEntity(ContentTypes.`text/html(UTF-8)`,
            """<a href='/hello'>Hello</a><br/>
              |<form action="/create-item/1" method="post">
              |  <input type="submit" value="Create Item 1"/>
              |</form>
              |<a href='/item/1'>Item FindById</a>
              |""".
              stripMargin)
        }
      }
    } ~ path("hello") {
      get {
        complete {
          //Future(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>").toStrict(duration)
        }
      }
    } ~ get {
      pathPrefix("item" / LongNumber) { id =>
        onSuccess(fetchItem(id)) {
          case Some(item) => complete(item)
          case None => complete(StatusCodes.NotFound, s"Not Found:${id}")
        }
      }
    } ~ post {
      pathPrefix("create-item" / LongNumber) { id =>
        onComplete(saveOrder(id)) {
          _ => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Create Item is ok.</h1>0").toStrict(duration))
        }
      }
    } ~ path("302") {
      val locationHeader = headers.Location(
        "http://127.0.0.1/301")
      complete(HttpResponse(302, headers = List(
        locationHeader)))
    } ~
      path("301") {
        val locationHeader = headers.Location(
          "http://127.0.0.1/static/test.html")
        complete(HttpResponse(302, headers =
          List(locationHeader))
        )
      } ~ path("404") {
      complete(HttpResponse(404, entity =
        "Unfortunately, the resource couldn't be found."))
    } ~ get {
      pathPrefix(
        "static" / Segment) { name =>
        getFromFile(new
            File(BASE_DIR, name))
      }
    }
  }
}
