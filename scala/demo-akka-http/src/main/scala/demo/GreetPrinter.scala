package demo

import akka.actor.Actor

/**
  * Created by YaFengLi on 2016/12/6.
  */
class GreetPrinter extends Actor {
  def receive = {
    case Greeting(message) => println(message)
  }
}
