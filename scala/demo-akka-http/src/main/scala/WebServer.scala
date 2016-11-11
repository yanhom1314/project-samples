import java.util.concurrent.TimeUnit

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol._

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.io.StdIn

object WebServer {
  val cache = mutable.Map[Long, Item]()

  val HTTP_PORT = 80

  final case class Item(name: String, id: Long)

  implicit val itemFormat = jsonFormat2(Item)

  def fetchItem(itemId: Long): Future[Option[Item]] = Future(cache.get(itemId))

  def saveOrder(itemId: Long): Future[Done] = Future {
    cache += itemId -> Item(itemId.toString, itemId)
    Done
  }

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-demo-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route = path("hello") {
      get {
        complete {
          //Future(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>").toStrict(FiniteDuration(6, TimeUnit.SECONDS))
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
          done => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Create Item is ok.</h1>0").toStrict(FiniteDuration(5, TimeUnit.SECONDS)))
        }
      }
    }

    val bindingFuture = Http().bindAndHandle(route, "localhost", HTTP_PORT)

    println(s"Server online at http://localhost[:${HTTP_PORT}]/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}