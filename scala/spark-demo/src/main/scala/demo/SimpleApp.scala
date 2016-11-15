package demo

import org.apache.spark.{SparkConf, SparkContext}

object SimpleApp extends MyApp {

  (0 until args.length).foreach { i => println(s"$i:${args(i)}") }

  time {
    val logFile = if (args.length >= 1) args(0) else "/tmp/key.txt" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)

    val logData = sc.textFile(logFile, 2).cache()
    val numLs = logData.count()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()

    println("Lines with all:%s  Lines with a: %s, Lines with b: %s".format(numLs, numAs, numBs))
  }
}