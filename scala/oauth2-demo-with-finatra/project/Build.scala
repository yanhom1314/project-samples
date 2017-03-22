import sbt._
import scala.io._
import java.io._

object Build {
  val $ = Source.fromFile(new File("version.properties")).getLines.filter( l => !l.startsWith("#") && l.split("=").size ==2).map {
    _.split("=") match {
            case Array(k,v) => k.trim -> v.trim
        }
  } toMap
}