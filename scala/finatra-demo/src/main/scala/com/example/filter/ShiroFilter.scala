package com.example.filter

import java.text.SimpleDateFormat
import java.util.Date

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.Logging
import com.twitter.util.Future
import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.{ExpiredSessionException, UnknownSessionException}

class ShiroFilter extends SimpleFilter[Request, Response] with Logging {

  import Helpers._

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val currentUser = SecurityUtils.getSubject()
    val session = currentUser.getSession()
    try {
      println(s"id:${session.getId} auth:${currentUser.isAuthenticated} time:${new Date().format("yyyy-MM-dd HH:mm:ss")}")
      session.touch()
      if (currentUser.isAuthenticated) {
        println(s"id:${session.getId} lastAccessTime:${session.getLastAccessTime.format("yyyy-MM-dd HH:mm:ss")}")
        service(request)
      }
      else {
        request.response.statusCode = Status.GatewayTimeout.code
        Future(request.response)
      }
    } catch {
      case e: ExpiredSessionException =>
        println(s"ExpiredSessionException:${session.getId}")
        try {
          currentUser.logout()
        } catch {
          case e: UnknownSessionException => println(s"UnknownSessionException:${session.getId}")
        }
        request.response.statusCode = Status.GatewayTimeout.code
        Future(request.response)
    }
  }
}

object Helpers {

  implicit class DateMix(val date: Date) {
    def format(fmt: String): String = {
      val s = new SimpleDateFormat(fmt)
      s.format(date)
    }
  }

}