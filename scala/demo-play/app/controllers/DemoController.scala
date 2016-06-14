package controllers

import javax.inject._

import entities.TPersonRepository
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import services.SpringContextLoader

import scala.collection.JavaConversions._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DemoController @Inject()(val sc: SpringContextLoader, val personRepository: TPersonRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  import sc._

  def demo() = Action { implicit request =>
    println("################################")
    ctx.getBeanDefinitionNames.foreach( n => println(s"name:${n} type:${ctx.getBean(n).getClass.getName}"))
    println("################################")
    val repo = bean(classOf[TPersonRepository])
    println(s"repo:${repo} count:${repo.count()}")
    println("################################")
    println(s"personRepository:${personRepository.count()}")
    println("################################")
    repo.findAll().foreach(t => println(s"id:${t.id} name:${t.name} age:${t.age}"))
    Ok(views.html.demo())
  }
}
