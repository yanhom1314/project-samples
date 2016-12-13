package controllers

import javax.inject._

import entities.TPersonRepository
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc._
import services.SpringContextLoader
import third.DemoData
import third.datatables.DataTablesData
import third.jqgrid.JqGridData

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

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

  def jqgrid() = Action { implicit request =>
    import JqGridData._
    val list = JqGridData(2, 1, 13, ListBuffer[DemoData]())
    (0 to 12).foreach { i => list.rows += DemoData(i, s"firstName:${i}", s"lastName:${i}", s"address:${i}") }
    Ok(Json.toJson(list))
  }

  def datatables(draw: Int, start: Int, length: Int) = Action { implicit request =>
    import third.datatables.DataTablesData._

    request.queryString.toList.sortBy(t => t._1).foreach { t => println(t._1 + ":" + t._2.map(v => v).mkString(" ")) }

    val list = DataTablesData(draw, 102, 102, ListBuffer[DemoData]())
    (start.toInt until start.toInt + length).foreach { i => list.data += DemoData(i, s"firstName:${i}", s"lastName:${i}", s"address:${i}") }
    Ok(Json.toJson(list))
  }

  def save = Action(parse.json) { request =>
    Ok("Got: " + (request.body \ "name").as[String])
  }
}
