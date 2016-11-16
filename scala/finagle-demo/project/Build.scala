import scala.io._

object Build {
  val l_r="""^([^=]+)=([^=]+)$""".r
  val $=Source.fromFile("project/build.properties").getLines.filter(_.split("=").length == 2).map(_.split("="))
              .map { case Array(k,v) =>k -> v }.toMap
}