package demo.boot

object DBigInt extends App {
  def fac(n: BigInt): BigInt = {
    n.intValue() match {
      case 0 => 1
      case _ => n * fac(n - 1)
    }
  }

  println(fac(200))
  (0 to 10).foreach(println(_))
  println("######################")
  (0 to 10).toStream.foreach(println(_))
  println("######################")
  (0 to 10).toStream.foreach(println(_))
}
