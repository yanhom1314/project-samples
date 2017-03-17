package demo

import scala.io._

object Del extends App {
  Source.fromFile("/tmp/un.txt").getLines.map(_.trim).foreach { un =>
    println(s"select u.username as username,u.id as user_id,c.id as company_id from gb_dns_users u right join gb_dns_companies c on c.user_id=u.id where u.username='${un}';")
  }
}