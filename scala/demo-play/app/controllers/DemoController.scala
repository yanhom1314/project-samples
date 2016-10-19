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
class DemoController @Inject()(val sc: SpringContextLoader, val personRepo: TPersonRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def demo() = Action { implicit request =>
    println("################################")
    println(s"personRepository:${personRepo.count()}")
    println("################################")
    personRepo.findAll().foreach(t => println(s"id:${t.id} name:${t.name} age:${t.age}"))
    println("################################")
    personRepo.findAllByAge(12)
    println(s"age > 12 :${personRepo.findAllByAge(12)}")
    println("################################")
    Ok(views.html.demo())
  }
}
