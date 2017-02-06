package controllers

import javax.inject._

import entities.TPersonRepository
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc._
import services.SpringContextLoader
import third.DemoData
import third.datatables.DataTablesData
import third.jqgrid.{JqGridData, JqGridForm}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DemoController @Inject()(val sc: SpringContextLoader, val personRepo: TPersonRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val jqGridForm = Form[JqGridForm](
    mapping(
      "_search" -> boolean,
      "nd" -> longNumber,
      "rows" -> number,
      "page" -> number,
      "sidx" -> text,
      "sord" -> text,
      "searchField" -> optional(text),
      "searchString" -> optional(text),
      "searchOper" -> optional(text),
      "filters" -> optional(text)
    )(JqGridForm.apply)(JqGridForm.unapply)
  )

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
    jqGridForm.bindFromRequest().fold(_ => Results.BadRequest, o => {
      import JqGridData._

      Logger.info(s"_search:${o._search} nd:${o.nd} rows:${o.rows} page:${o.page} sidx:${o.sidx} sord:${o.sord} searchField:${o.searchField} searchString:${o.searchString} searchOper:${o.searchOper}")
      val start = o.rows * (o.page - 1) + 1
      val records = 36

      val list = JqGridData(Math.ceil(records.toDouble / o.rows).toInt, o.page, records, ListBuffer[DemoData]())
      (start to (if (start + o.rows <= records) start + o.rows else records)).foreach { i => list.rows += DemoData(i, s"firstName:${i}", s"lastName:${i}", s"address:${i}") }

      Ok(Json.toJson(list))
    })
  }

  def datatables(draw: Int, start: Int, length: Int) = Action { implicit request =>
    import third.datatables.DataTablesData._

    request.queryString.toList.sortBy(t => t._1).foreach { t => Logger.info(t._1 + ":" + t._2.map(v => v).mkString(" ")) }

    val list = DataTablesData(draw, 102, 102, ListBuffer[DemoData]())
    (start.toInt until start.toInt + length).foreach { i => list.data += DemoData(i, s"firstName:${i}", s"lastName:${i}", s"address:${i}") }
    Ok(Json.toJson(list))
  }

  def save = Action(parse.json) { request =>
    Ok("Got: " + (request.body \ "name").as[String])
  }
}
