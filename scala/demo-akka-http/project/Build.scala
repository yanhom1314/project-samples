import scala.io.Source

object Build {
  val l_r = """^(.+)=(.+)$""".r
  val $ = Source.fromFile("project/build.properties").getLines.filter(l_r.findFirstMatchIn(_).isDefined).map(l_r.findFirstMatchIn(_).get).map { m =>
    m.group(1).trim -> m.group(2).trim
  }.toMap
}

