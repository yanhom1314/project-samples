package controllers

import javax.inject._

import models.TPersonRepository
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import services.SpringContextLoader

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DemoController @Inject()(val springContextLoader: SpringContextLoader, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  import springContextLoader.ctx

  def demo() = Action { implicit request =>
    println(springContextLoader.ctx)
    if (springContextLoader.ctx != null) {
      println("################################")
      springContextLoader.ctx.getBeanDefinitionNames.foreach(println(_))
      println("################################")
      val repo = ctx.getBean(classOf[TPersonRepository])
      println(s"repo:${repo} count:${repo.count()}")
      println("################################")
      import scala.collection.JavaConversions._
      repo.findAll().foreach(t => println(s"id:${t.id} name:${t.name} age:${t.age}"))
    }
    Ok(views.html.demo())
  }
}
