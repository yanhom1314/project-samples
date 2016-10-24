package com.example.freemarker

import java.util.concurrent.ConcurrentHashMap

import com.twitter.finatra.response.Mustache

import scala.collection.JavaConverters._

/**
  * Created by LYF on 2016/10/23.
  */
private[freemarker] class FreemarkerTemplateNameLookup {
  private val classToTemplateNameCache = new ConcurrentHashMap[Class[_], String]().asScala

  /* Public */

  def getTemplateName(obj: Any): String = {
    obj match {
      case fbc: FreemarkerBodyComponent if !fbc.template.isEmpty => fbc.template
      case fbc: FreemarkerBodyComponent => lookupViaAnnotation(fbc.data)
      case _ => lookupViaAnnotation(obj)
    }
  }

  /* Private */

  private def lookupViaAnnotation(viewObj: Any): String = {
    classToTemplateNameCache.getOrElseUpdate(viewObj.getClass, {
      val mustacheAnnotation = viewObj.getClass.getAnnotation(classOf[Mustache])
      mustacheAnnotation.value + ".mustache"
    })
  }
}
