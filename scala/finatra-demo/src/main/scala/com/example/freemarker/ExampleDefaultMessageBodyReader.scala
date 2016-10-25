package com.example.freemarker

import javax.inject.Inject

import com.fasterxml.jackson.databind.node.ObjectNode
import com.google.inject.{Injector, Singleton}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.internal.marshalling.RequestInjectableValues
import com.twitter.finatra.http.marshalling.DefaultMessageBodyReader
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.finatra.request.JsonIgnoreBody

/**
  * Created by YaFengLi on 2016/10/25.
  */
@Singleton
class ExampleDefaultMessageBodyReader @Inject()(injector: Injector, objectMapper: FinatraObjectMapper) extends DefaultMessageBodyReader {
  override def parse[T](request: Request)(implicit evidence$1: Manifest[T]): T = {
    val requestAwareObjectReader = {
      val requestInjectableValues = new RequestInjectableValues(objectMapper, request, injector)
      objectMapper.reader[T].`with`(requestInjectableValues)
    }

    val length = request.contentLength.getOrElse(0L)
    if (length > 0 && isJsonEncoded(request) && !ignoresBody)
      FinatraObjectMapper.parseRequestBody(request, requestAwareObjectReader)
    else
      requestAwareObjectReader.readValue(ExampleDefaultMessageBodyReader.EmptyObjectNode)
  }

  /* Private */

  private def ignoresBody[T: Manifest]: Boolean = {
    manifest[T].runtimeClass.isAnnotationPresent(classOf[JsonIgnoreBody])
  }

  private def isJsonEncoded(request: Request): Boolean = {
    request.contentType.exists { contentType =>
      contentType.startsWith("application/json")
    }
  }
}

private[freemarker] object ExampleDefaultMessageBodyReader {
  private val EmptyObjectNode = new ObjectNode(null)
}