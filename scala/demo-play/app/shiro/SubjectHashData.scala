package shiro

import java.util.concurrent.ConcurrentHashMap

import org.apache.shiro.subject.Subject

/**
  * can use like Redis Or PostgreSQL Database System save the info.
  */
object SubjectHashData {

  val cache = new ConcurrentHashMap[String, Subject]()

  def save(un: String, subject: Subject) = {
    if (cache.containsKey(un)) cache.remove(un)
    cache.put(un, subject)
  }

  def logout(un: String) = {
    if (cache.containsKey(un)) {
      val subject = cache.get(un)
      cache.remove(un)
      subject.logout()
    }
  }

  def get(un: String): Option[Subject] = {
    println(s"un:${un} ${cache.get(un).hasRole("ROLE_ADMIN")}")
    if (cache.containsKey(un)) Some(cache.get(un)) else None
  }
}
