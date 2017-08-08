package third.jqgrid

import play.api.data.Forms._
import play.api.data._

/**
  * @tparam A 记录类型
  */
trait JqGridData[A] {

  /**
    * @return 总页数
    */
  def total: Int

  /**
    *
    * @return 当前页码
    */
  def page: Int

  /**
    *
    * @return 总记录数
    */
  def records: Int

  /**
    *
    * @return 当前页记录有序集合
    */
  def rows: Seq[A]
}

case class JqGridForm(var _search: Boolean, val nd: Long, val rows: Int, val page: Int, val sidx: String,
                      val sord: String, val searchField: Option[String], val searchString: Option[String],
                      val searchOper: Option[String], val filters: Option[String])

object JqGridData {
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
}