import java.io.File
import scala.io.Source

object Build {
    val $ = Source.fromFile(new File("project/build.properties")).getLines.filter(_.split("=").length == 2).map { 
        _.split("=") match {
            case Array(k,v) => k.trim -> v.trim
        }
    } toMap
}

