package com.example.filter

import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

/**
  * Created by YaFengLi on 2016/10/24.
  */
class SecureFilter extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {

    if (!request.params.contains("name")) {
      request.response.status_=(Status.Forbidden)
      Future {
        request.response
      }
    }
    else service(request)
  }
}
