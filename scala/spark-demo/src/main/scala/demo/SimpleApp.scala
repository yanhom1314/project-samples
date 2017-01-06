package demo

import org.apache.spark.{SparkConf, SparkContext}

object SimpleApp extends App {

  final val split_str = """|"""
  final val ip = "106.59.58.20"

  implicit object ArrayString extends ArrayStringOrdering

  if (args.length >= 1) {
    val logFile = args(0)
    // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numLs = logData.count()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()

    println("##########>> Lines with all:%s  Lines with a: %s, Lines with b: %s logData:%s".format(numLs, numAs, numBs, logData.isEmpty()))

    val m = logData.filter { t => val a = t.split(split_str); a != null && a.length == 2 }
    if (!m.isEmpty()) {
      m.map { t => val s = t.split("\\|"); Array(s(0).trim.toLowerCase, s(1).trim) }.top(10).foreach {
        case Array(d, ip) if d.trim.length > 1 && ip.trim.length > 1 => println(s"domain:$d ip:$ip")
        case e => println(s"ERROR:$e")
      }
    }

    val ip_m = logData.filter(_.contains(ip)).map(_ => ip -> 1)
    ip_m.reduceByKey(_ + _).saveAsTextFile("file:/tmp/test_result")
  }
  else sys.error(s"Usage:./spark-submit --master spark://172.16.9.43:7707 --class demo.SimpleApp x.y.z.jar [/path/file]")
}

trait ArrayStringOrdering extends Ordering[Array[String]] {
  override def compare(x: Array[String], y: Array[String]): Int = x(1).compareTo(y(1))
}

