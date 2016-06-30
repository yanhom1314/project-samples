package tutorial.webapp

import scala.scalajs.js.JSApp
import scala.scalajs.js
import js.DynamicImplicits._
import js.Dynamic.{global => g}

object TutorialApp extends JSApp {

  def main(): Unit = {
    val console = g.require("better-console")
    //console.error("oops")
    println("Hello world!")
  }
}
