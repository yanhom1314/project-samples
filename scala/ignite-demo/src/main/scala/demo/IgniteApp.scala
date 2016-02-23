package demo

import org.apache.ignite.{Ignite, Ignition}

trait IgniteApp extends App {

  def withClientIgnite(call: Ignite => Unit): Unit = {
    val ignite = Ignition.start()
    try {
      call(ignite)
    }
    catch {
      case e: Exception => e.printStackTrace()
    } finally {
      ignite.close()
    }
  }


  def time(call:()=>Unit):Unit = {
    val start  = System.currentTimeMillis()
    try{call()}catch {case e:Exception => e.printStackTrace()}
    val end  = System.currentTimeMillis()
    println(s"time use ${end - start}ms.")
  }
}
