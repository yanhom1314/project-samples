package controllers

import javax.inject._

import play.api.mvc._
import services.Counter

@Singleton
class CountController @Inject()(counter: Counter) extends InjectedController {
  def count = Action {
    Ok(counter.nextCount().toString)
  }
}
