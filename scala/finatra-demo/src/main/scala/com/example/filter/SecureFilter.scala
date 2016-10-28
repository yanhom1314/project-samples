package com.example.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.twitter.finagle.http.{ MediaType, Status, Version }
import com.twitter.finagle.{ Filter, OAuth2, OAuth2Request, Service, SimpleFilter }
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.finagle.oauth2._
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.util.Future
/**
 * Created by YaFengLi on 2016/10/24.
 */
class SecureFilter[U](dataHandler: DataHandler[U]) extends Filter[Request, Response, OAuth2Request[U], Response] with OAuth2 with OAuthErrorHandler {

  override def handleError(e: OAuthError) = e.toHttpResponse

  override def apply(request: Request, service: Service[OAuth2Request[U], Response]): Future[Response] = {
    val mapper = new FinatraObjectMapper(new ObjectMapper() with ScalaObjectMapper)
    if (request.path == "v1/auth") {
      issueAccessToken(request, dataHandler: DataHandler[U]) flatMap { token =>
        val resp = request.response
        resp.version = Version.Http11
        resp.status = Status.Ok
        resp.setContentType(MediaType.Json, "utf-8")
        resp.setContentString(mapper.writeValueAsString(token))
        Future.value(request.response)
      } handle {
        case e: OAuthError => e.toHttpResponse
      }
    } else {
      authorize(request, dataHandler) flatMap { authInfo =>
        service(OAuth2Request(authInfo, request))
      } handle {
        case e: OAuthError => handleError(e)
      }
    }
  }
}
