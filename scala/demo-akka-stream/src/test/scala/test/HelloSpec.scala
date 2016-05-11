package test

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source, ZipWith}
import akka.stream.{ActorMaterializer, ClosedShape, UniformFanInShape}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Await, Future}

/**
  * Created by YaFengLi on 2016/5/10.
  */
class HelloSpec extends FlatSpec with Matchers {

  "A Source" should "sink" in {
    implicit val system = ActorSystem("testSystem")
    implicit val materializer = ActorMaterializer()
    implicit val ex = system.dispatcher

    println("#######################")
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach[Int](t => println(t)))
    Source(1 to 6).via(Flow[Int].map(_ * 2)).to(Sink.foreach(println(_)))

    // Starting from a Source
    val source = Source(1 to 6).map(_ * 2)
    source.to(Sink.foreach(println(_))).run()

    // Starting from a Sink
    val sink: Sink[Int, NotUsed] = Flow[Int].map(_ * 2).to(Sink.foreach(println(_)))
    Source(1 to 6).to(sink).run()
    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] =
      Flow[Int].alsoTo(Sink.foreach(println(_))).to(Sink.ignore)
    Source(1 to 6).to(otherSink).run()

    Source(List(1, 2, 3))
      .map(_ + 1).async
      .map(_ * 2)
      .to(Sink.ignore).run()

    import akka.stream.Fusing

    val flow = Flow[Int].map(_ * 2).filter(_ > 500)
    val fused = Fusing.aggressive(flow)

    Source.fromIterator { () => Iterator from 0 }
      .via(fused)
      .take(10).to(Sink.foreach(println(_))).run()

    val pickMaxOfThree = GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._

      val zip1 = b.add(ZipWith[Int, Int, Int](math.max _))
      val zip2 = b.add(ZipWith[Int, Int, Int](math.max _))
      zip1.out ~> zip2.in0

      UniformFanInShape(zip2.out, zip1.in0, zip1.in1, zip2.in1)
    }

    val resultSink = Sink.head[Int]

    val g = RunnableGraph.fromGraph(GraphDSL.create(resultSink) { implicit b =>
      sink =>
        import GraphDSL.Implicits._

        // importing the partial graph will return its shape (inlets & outlets)
        val pm3 = b.add(pickMaxOfThree)

        Source.single(1) ~> pm3.in(0)
        Source.single(2) ~> pm3.in(1)
        Source.single(3) ~> pm3.in(2)
        pm3.out ~> sink.in
        ClosedShape
    })
    import scala.concurrent.duration._

    val max: Future[Int] = g.run()
    Await.result(max, 10.seconds) should equal(3)
    println("#######################")
  }
}
