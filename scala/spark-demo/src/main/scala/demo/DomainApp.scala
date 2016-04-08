package demo

import org.apache.spark.{SparkConf, SparkContext}

object DomainApp extends MyApp {

  implicit object ArrayString extends ArrayStringOrdering

  def main(args: Array[String]) {
    time {
      val logFile = if (args.length >= 1) args(0) else "/phd/export/dns/sdun_1_20160329.log.ok" // Should be some file on your system
      val conf = new SparkConf().setAppName("Domain Application")
      val sc = new SparkContext(conf)
      val split_str = "\\|"

      val logData = sc.textFile(logFile, 2).cache()

      logData.filter(_.split(split_str).length == 2).map { t => val s = t.split("\\|"); Array(s(0).trim.toLowerCase, s(1).trim) }.top(10).foreach {
        case Array(d, ip) if d.trim.length > 1 && ip.trim.length > 1 => println(s"domain:$d ip:$ip")
        case e => println(s"ERROR:$e")
      }
    }
  }
}

trait ArrayStringOrdering extends Ordering[Array[String]] {
  override def compare(x: Array[String], y: Array[String]): Int = x(1).compareTo(y(1))
}