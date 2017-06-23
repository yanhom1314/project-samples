package shiro

import javax.inject.{Inject, Singleton}

import org.apache.shiro.subject.Subject
import play.api.Configuration
import play.api.cache.AsyncCacheApi

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * can use like Redis Or PostgreSQL Database System save the info.
  */
@Singleton
class ShiroSubjectCache @Inject()(conf: Configuration, cache: AsyncCacheApi) {
  val time_out = conf.get[Long]("play.http.session.maxAge")

  def save(un: String, subject: Subject) = {
    cache.set(un, subject, time_out.milliseconds)
  }

  def logout(un: String) = {
    cache.get[Subject](un).foreach(sub => sub.get.logout())
    cache.remove(un)
  }

  def get(un: String): Option[Subject] = {
    Await.result(cache.get[Subject](un), 5.seconds)
  }
}
