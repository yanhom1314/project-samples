package demo

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingApp extends App {

  val hostname = if (args.length >= 1) args(0) else "localhost"
  val port = if (args.length >= 2) args(1).toInt else 9999
  println(s"listening ${hostname}:${port}")

  val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(60))

  val lines = ssc.socketTextStream(hostname, port)

  val words = lines.flatMap(_.split(" "))

  val pairs = words.map(word => (word, 1))
  val wordCounts = pairs.reduceByKey(_ + _)

  // Print the first ten elements of each RDD generated in this DStream to the console
  //wordCounts.print()
  wordCounts.saveAsTextFiles("file:///tmp/test", "hello")

  ssc.start() // Start the computation
  ssc.awaitTermination() // Wait for the com
}
