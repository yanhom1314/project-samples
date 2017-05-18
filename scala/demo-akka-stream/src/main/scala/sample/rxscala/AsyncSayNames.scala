package sample.rxscala

import rx.lang.scala.Observable

object AsyncSayNames extends App {

  def hello(names: String*) {
    Observable.from(names).subscribe { n =>
      println(s"Hello $n!")
    }
  }

  hello(Array("lyf", "yfl"): _*)
}