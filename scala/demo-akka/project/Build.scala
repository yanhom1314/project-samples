import sbt._

import scala.io._

object Build extends Build {
  val _r = "^(.+)=(.+)$".r
  val $ = Source.fromFile("./version.properties").getLines().toSeq.filter(_r.findFirstMatchIn(_).isDefined).map {
    line =>
      val m = _r.findFirstMatchIn(line).head
      (m.group(1).trim -> m.group(2).trim)
  }.toMap
}