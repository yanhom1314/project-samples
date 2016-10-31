package com.example.filter

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.Logging
import com.twitter.util.Future
import org.apache.shiro.SecurityUtils

/**
  * Created by YaFengLi on 2016/10/31.
  */
class ShiroFilter extends SimpleFilter[Request, Response] with Logging {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val currentUser = SecurityUtils.getSubject()
    if (currentUser.isAuthenticated) {
      service(request)
    }
    else {
      request.response.statusCode = Status.Unauthorized.code
      Future(request.response)
    }
  }
}
