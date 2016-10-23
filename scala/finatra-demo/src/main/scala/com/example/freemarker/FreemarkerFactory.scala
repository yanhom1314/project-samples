package com.example.freemarker

import java.io.File
import javax.inject.Singleton

import freemarker.template.{Configuration, TemplateExceptionHandler}

/**
  * Created by LYF on 2016/10/23.
  */
@Singleton
class FreemarkerFactory {
  val configuration = new Configuration(Configuration.VERSION_2_3_25)

  def apply() {
    configuration.setDirectoryForTemplateLoading(new File("src/resources/templates"))
    configuration.setDefaultEncoding(sys.props.get("file.encoding").getOrElse("UTF-8"))
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    configuration.setLogTemplateExceptions(false)
  }
}
