package com.example.freemarker

import java.io.{ByteArrayOutputStream, File, OutputStreamWriter, StringWriter}
import java.nio.charset.StandardCharsets
import javax.inject.Singleton

import com.twitter.io.Buf
import freemarker.template.{Configuration, TemplateExceptionHandler}

/**
  * Created by LYF on 2016/10/23.
  */
@Singleton
class FreemarkerService {
  val cfg = new Configuration(Configuration.VERSION_2_3_25)
  cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"))
  cfg.setDefaultEncoding("UTF-8")
  cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
  cfg.setLogTemplateExceptions(false)

  private[freemarker] def createBuffer(templateName: String, obj: Any): Buf = {
    val mustache = cfg.getTemplate(templateName)

    val outputStream = new ByteArrayOutputStream(1024)
    val writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
    try {
      mustache.execute(writer, obj)
    } finally {
      writer.close()
    }

    Buf.ByteArray.Owned(outputStream.toByteArray)
  }

  def createString(templateName: String, obj: Any): String = {
    val mustache = mustacheFactory.compile(templateName)

    val writer = new StringWriter()
    mustache.execute(writer, obj).flush()

    writer.toString
  }
}
