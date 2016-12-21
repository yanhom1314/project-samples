package shiro

import java.util.concurrent.ConcurrentHashMap

import org.apache.shiro.subject.Subject

/**
  * can use like Redis Or PostgreSQL Database System save the info.
  */
object SubjectHashData {
  private val data = new ConcurrentHashMap[String, Subject]()

  def save(un: String, subject: Subject) = {
    data.put(un, subject)
  }

  def logout(un: String) = {
    if (data.containsKey(un)) {
      val subject = data.get(un)
      data.remove(un)
      subject.logout()
    }
  }

  def get(un: String): Option[Subject] = {
    if (data.containsKey(un)) Some(data.get(un)) else None
  }
}
