package demo

import akka.actor.Actor

/**
  * Created by YaFengLi on 2016/12/6.
  */
class Greeter extends Actor {
  var greeting = ""

  def receive = {
    case WhoToGreet(who) => greeting = s"hello, $who"
    case Greet => sender ! Greeting(greeting) // Send the current greeting back to the sender
  }
}
