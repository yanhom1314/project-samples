package third.jqgrid

case class JqGridForm(var _search: Boolean, val nd: Long, val rows: Int, val page: Int, val sidx: String, val sord: String, val searchField: Option[String], val searchString: Option[String], val searchOper: Option[String], val filters: Option[String])