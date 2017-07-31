package third.jqgrid

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Json, _}
import third.DemoData

import scala.collection.mutable.ListBuffer

/**
  * @param total   总页数
  * @param page    第几页
  * @param records 总记录数
  * @param rows    当前页记录
  * @tparam A 记录类型
  */
case class JqGridData[A](total: Int, page: Int, records: Int, rows: Seq[A])

object JqGridData {
  implicit val demoDataFormat = Json.format[DemoData]
  implicit val jqGridDataFormat: Format[JqGridData] = (
    (__ \ 'total).format[Int] and
      (__ \ 'page).format[Int] and
      (__ \ 'records).format[Int] and
      (__ \ 'rows).formatNullable[ListBuffer[DemoData]].inmap[ListBuffer[DemoData]](
        o => o.getOrElse(ListBuffer.empty[DemoData]),
        s => if (s.isEmpty) None else Some(s)
      )
    ) (JqGridData.apply, unlift(JqGridData.unapply))
}
