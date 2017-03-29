package demo

import java.io.File

import scala.io._

object IpFindDomain extends App {
  if (args.length >= 2) {
    val list = Source.fromFile(new File(args(0))).getLines().map(_.trim).toSet

    Source.fromFile(new File(args(1))).getLines().foreach { l =>
      try {
        l.split("\\|") match {
          case Array(d, ips) => ips.split(";").filter(list.contains(_)).foreach(ip => println(s"${d} ${ip}"))
          case _ =>
        }
      } catch {
        case _: Exception =>
      }
    }
  }
  else println(args.length)
}
