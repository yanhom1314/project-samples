package demo

trait MyApp {
  def time[T](t: T): Unit = {
    val start = System.currentTimeMillis()
    t match {
      case _ =>
        val end = System.currentTimeMillis()
        println(s"time use ${end - start}ms.")
    }
  }
}
