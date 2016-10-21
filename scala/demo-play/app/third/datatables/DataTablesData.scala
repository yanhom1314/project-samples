package third.datatables

import play.api.libs.json._
import third.DemoData

import scala.collection.mutable.ListBuffer

/**
  * Created by YaFengLi on 2016/10/21.
  */
case class DataTablesData(data: ListBuffer[DemoData])

object DataTablesData {
  implicit val dataTablesDataFormat = Json.format[DataTablesData]
  //  implicit val dataTablesDataFormat: Format[DataTablesData] = (
  //    (__ \ 'data).formatNullable[ListBuffer[DemoData]].inmap[ListBuffer[DemoData]](
  //      o => o.getOrElse(ListBuffer.empty[DemoData]),
  //      s => if (s.isEmpty) None else Some(s)
  //    )
  //    ) (DataTablesData.apply, unlift(DataTablesData.unapply))
}
