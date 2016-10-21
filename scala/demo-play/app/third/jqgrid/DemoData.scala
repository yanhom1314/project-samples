package third.jqgrid

import play.api.libs.json.Json

case class DemoData(id: Int, firstName: String, lastName: String, address: String)

object DemoData {
  implicit val demoDataFormat = Json.format[DemoData]
}