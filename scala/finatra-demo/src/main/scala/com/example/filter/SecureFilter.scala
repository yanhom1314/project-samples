package com.example.filter

import javax.inject.Inject

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.oauth2._
import com.twitter.finagle.{OAuth2, Service, SimpleFilter}
import com.twitter.util.Future

class SecureFilter @Inject()(dataHandler: OAuthDataHandler) extends SimpleFilter[Request, Response] with OAuth2 {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    authorize(request, dataHandler) flatMap { _ =>
      service(request)
    } handle {
      case e: OAuthError =>
        e.toHttpResponse
    }
  }
}
