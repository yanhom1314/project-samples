package third.jqgrid.ext

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, _}
import third.DemoData
import third.jqgrid.JqGridData

case class JqGridDataDemoData(total: Int, page: Int, records: Int, rows: Seq[DemoData]) extends JqGridData[DemoData]

object JqGridDataDemoData {
  implicit val demoDataFormat = Json.format[DemoData]

  implicit val jqGridDataDemoDataFormat: Format[JqGridDataDemoData] = (
    (__ \ 'total).format[Int] and
      (__ \ 'page).format[Int] and
      (__ \ 'records).format[Int] and
      (__ \ 'rows).formatNullable[Seq[DemoData]].inmap[Seq[DemoData]](
        o => o.getOrElse(Seq.empty[DemoData]),
        s => if (s.isEmpty) None else Some(s)
      )
    ) (JqGridDataDemoData.apply, unlift(JqGridDataDemoData.unapply))
}
