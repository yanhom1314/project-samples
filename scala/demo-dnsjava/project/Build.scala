import scala.io.Source

object Build {
  private val EQ_STR = "="
  val $ = Source.fromFile("project/build.properties").getLines().filter(l => l.split(EQ_STR).length == 2).map {
    l =>
      val s = l.split(EQ_STR)
      s(0).trim -> s(1).trim
  } toMap
}