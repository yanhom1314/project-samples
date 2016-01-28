package demo

import org.apache.ignite.Ignition

object LocalGettingStarted extends App {
  println("Hello World!")
  val ignite = Ignition.start("config/example-ignite.xml")
  println(ignite)
}
