package demo

import org.apache.spark.{SparkConf, SparkContext}

object SimpleApp extends App {

  val split_str = "\\|"

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

    println("Lines with all:%s  Lines with a: %s, Lines with b: %s".format(numLs, numAs, numBs))

    logData.filter(_.split(split_str).length == 2).map { t => val s = t.split("\\|"); Array(s(0).trim.toLowerCase, s(1).trim) }.top(10).foreach {
      case Array(d, ip) if d.trim.length > 1 && ip.trim.length > 1 => println(s"domain:$d ip:$ip")
      case e => println(s"ERROR:$e")
    }
  }
  else sys.error(s"Usage:./spark-submit --class demo.SimpleApp x.y.z.jar [/path/file]")
}

trait ArrayStringOrdering extends Ordering[Array[String]] {
  override def compare(x: Array[String], y: Array[String]): Int = x(1).compareTo(y(1))
}