package demo.dead

import akka.actor.{Actor, DeadLetter}

class DeadLetterListener extends Actor {
  def receive = {
    case d: DeadLetter => println(s"#################${d.message} from ${d.sender.path.toString}")
  }
}
