package shiro

import javax.inject.{Inject, Singleton}

import org.apache.shiro.subject.Subject
import play.api.Configuration
import play.api.cache.CacheApi

import scala.concurrent.duration._

/**
  * can use like Redis Or PostgreSQL Database System save the info.
  */
@Singleton
class ShiroSubjectCache @Inject()(conf: Configuration, cache: CacheApi) {
  val time_out = conf.getLong("play.http.session.maxAge").getOrElse(300000L)

  def save(un: String, subject: Subject) = {
    cache.set(un, subject, time_out.milliseconds)
  }

  def logout(un: String) = {
    cache.get[Subject](un).foreach(sub => sub.logout())
    cache.remove(un)
  }

  def get(un: String): Option[Subject] = {
    cache.get[Subject](un)
  }
}
