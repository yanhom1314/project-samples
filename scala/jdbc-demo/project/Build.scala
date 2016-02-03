import sbt._
import scala.io._

object Build {
  val r = """(.+)=(.+)""".r
  val $ = Source.fromFile("project/build.properties").getLines.filter(r.findFirstMatchIn(_).isDefined).map(r.findFirstMatchIn(_).get).map(m => m.group(1) -> m.group(2)).toMap
}
