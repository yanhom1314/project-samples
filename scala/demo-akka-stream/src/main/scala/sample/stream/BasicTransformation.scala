package sample.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

object BasicTransformation {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Sys")

    implicit val materializer = ActorMaterializer()

    val text =
      """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.
        |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
        |when an unknown printer took a galley of type and scrambled it to make a type
        |specimen book.""".stripMargin

    val it = text.split("\\s").iterator
  }
}
