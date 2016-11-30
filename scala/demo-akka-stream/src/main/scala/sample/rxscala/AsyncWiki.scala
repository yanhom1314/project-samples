package sample.rxscala

import java.net.URL
import java.util.Scanner

import akka.actor.ActorSystem
import rx.lang.scala.Observable

import scala.concurrent._

object AsyncWiki extends App {
  implicit val system = ActorSystem("rxscala")

  import system.dispatcher

  /*
   * Fetch a list of Wikipedia articles asynchronously.
   */
  def fetchWikipediaArticleAsynchronously(wikipediaArticleNames: String*): Observable[String] = {
    Observable(subscriber => {
      Future {
        for (articleName <- wikipediaArticleNames) {
          if (!subscriber.isUnsubscribed) {
            val url = "https://en.wikipedia.org/wiki/" + articleName
            val art = new Scanner(new URL(url).openStream()).useDelimiter("\\A").next()
            subscriber.onNext(art)
          }
        }
        if (!subscriber.isUnsubscribed) {
          subscriber.onCompleted()
        }
      } onComplete (_ => system.terminate())
    })
  }

  fetchWikipediaArticleAsynchronously("Tiger", "Elephant")
    .subscribe(art => println("--- Article ---\n" + art.substring(0, 125)))
}