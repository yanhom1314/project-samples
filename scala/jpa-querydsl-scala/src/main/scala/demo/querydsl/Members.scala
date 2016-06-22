package demo.querydsl

object Members extends QMembers("members") {
  override def as(variable: String) = new QMembers(variable)
  
}

/**
 * Members is a Querydsl bean type
 */
class Members {

  var createdAt: java.sql.Timestamp = _

  var id: Integer = _

  var name: String = _

}

