package com.example.filter

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.Logging
import com.twitter.util.Future
import org.apache.shiro.SecurityUtils

class ShiroFilter extends SimpleFilter[Request, Response] with Logging {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val currentUser = SecurityUtils.getSubject()
    val session = currentUser.getSession()
    try {
      println(s"auth:${currentUser.isAuthenticated}")
      println(s"id:${session.getId}  ${session.getLastAccessTime}")
      if (currentUser.isAuthenticated) {
        session.touch()
        service(request)
      }
      else throw new Exception()
    } catch {
      case e: Exception =>
        if (currentUser.isAuthenticated) currentUser.logout()
        else session.stop()
        request.response.statusCode = Status.Unauthorized.code
        Future(request.response)
    }
  }
}
