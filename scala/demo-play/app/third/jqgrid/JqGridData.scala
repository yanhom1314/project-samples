package third.jqgrid

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Json, _}
import third.DemoData

import scala.collection.mutable.ListBuffer

case class JqGridData(total: Int, page: Int, records: Int, rows: ListBuffer[DemoData])

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
