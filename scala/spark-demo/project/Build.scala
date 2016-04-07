import java.io.File

import sbt._

object Build extends Build {
  val reg = "(.+)=(.+)".r
  //x.x.x.jar pattern
  val $ = scala.io.Source.fromFile(new File(System.getProperty("user.dir"), "project/build.properties")).getLines()
    .filter(reg.findFirstMatchIn(_).isDefined).map(reg.findFirstMatchIn(_).get).map(m => (m.group(1) -> m.group(2))).toMap
}