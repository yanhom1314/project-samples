package com.example.freemarker

import java.io.{ByteArrayOutputStream, OutputStreamWriter, StringWriter}
import java.nio.charset.StandardCharsets
import javax.inject.{Inject, Singleton}

import com.twitter.io.Buf

/**
  * Created by LYF on 2016/10/23.
  */
@Singleton
class FreemarkerService @Inject()(factory: FreemarkerFactory) {

  private[freemarker] def createBuffer(templateName: String, obj: Any): Buf = {
    val template = factory.configuration.getTemplate(templateName)

    val outputStream = new ByteArrayOutputStream(1024)
    val writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
    try {
      template.process(obj, writer)
    } finally {
      writer.close()
    }

    Buf.ByteArray.Owned(outputStream.toByteArray)
  }

  def createString(templateName: String, obj: Any): String = {
    val template = factory.configuration.getTemplate(templateName)

    val writer = new StringWriter()
    template.process(obj, writer)

    writer.toString
  }
}
