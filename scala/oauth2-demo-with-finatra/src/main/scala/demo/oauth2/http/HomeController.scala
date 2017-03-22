package demo.oauth2.http

import javax.inject.{Inject, Singleton}

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{JsonIgnoreBody, QueryParam}
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.{Max, PastTime, ValidationMessageResolver}
import org.joda.time.DateTime

@Singleton
class HomeController @Inject()(validationMessageResolver: ValidationMessageResolver) extends Controller {
  get("/") { _: Request =>
    response.ok.html("<h1>Hello World!</h1>")
  }
  get("/foo") { _: Request =>
    FooView("hello", List(Person("1", 1, "1"), Person("2", 2, "2")))
  }
  get("/authorize") { req: UsersRequest =>
    req.http.params.foreach(t => println(s"${t._1} -> ${t._2}"))
    req.http.headerMap.foreach(t => println(s"${t._1} -> ${t._2}"))

    UsersResponse(req.max, req.startDate, req.verbose)
  }
  get("/messages") { _: Request =>
    import scala.collection.JavaConverters._
    MessageView(validationMessageResolver.validationProperties.entrySet().asScala.map(e => Message(e.getKey.toString, e.getValue.toString)).toList)
  }
}


@Mustache("foo")
case class FooView(name: String, persons: List[Person])

case class Person(name: String, age: Int, address: String)

@Mustache("message")
case class MessageView(messages: List[Message])

case class Message(key: String, value: String)


case class UsersRequest(@Max(100) @QueryParam max: Int,
                        @PastTime @QueryParam startDate: Option[DateTime],
                        @QueryParam verbose: Boolean = false,
                        @JsonIgnoreBody @Inject() http: Request)

case class UsersResponse(max: Int, startDate: Option[DateTime], verbose: Boolean = false)

