package com.example.freemarker

import javax.inject.{Inject, Singleton}

import com.google.common.net.MediaType
import com.twitter.finatra.http.marshalling.{MessageBodyWriter, WriterResponse}

/**
  * Created by LYF on 2016/10/23.
  */
@Singleton
class FreemarkerMessageBodyWriter @Inject()(freemarkerService: FreemarkerService,
                                            templateLookup: FreemarkerTemplateNameLookup)
  extends MessageBodyWriter[Any] {
  override def write(obj: Any): WriterResponse = {
    WriterResponse(
      MediaType.HTML_UTF_8,
      freemarkerService.createBuffer(
        templateLookup.getTemplateName(obj),
        getScope(obj)))
  }

  /* Private */

  private def getScope(obj: Any): Any = {
    obj match {
      case c: FreemarkerBodyComponent => c.data
      case _ => obj
    }
  }
}
