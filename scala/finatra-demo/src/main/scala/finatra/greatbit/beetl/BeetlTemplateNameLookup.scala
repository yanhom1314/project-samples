package com.greatbit.tags.web.beetl

import java.util.concurrent.ConcurrentHashMap

import finatra.views.beetl.Beetl

import scala.collection.JavaConverters._

private[beetl] class BeetlTemplateNameLookup {
  private val classToTemplateNameCache = new ConcurrentHashMap[Class[_], String]().asScala
  private val suffix = if (System.getProperty("beetl.template.suffix") != null) System.getProperty("beetl.template.suffix") else ".html"

  /* Public */

  def getTemplateName(obj: Any): String = {
    obj match {
      case fbc: BeetlBodyComponent if !fbc.template.isEmpty => fbc.template
      case fbc: BeetlBodyComponent => lookupViaAnnotation(fbc.data)
      case _ => lookupViaAnnotation(obj)
    }
  }

  /* Private */

  private def lookupViaAnnotation(viewObj: Any): String = {
    classToTemplateNameCache.getOrElseUpdate(viewObj.getClass, {
      val freemarkerAnnotation = viewObj.getClass.getAnnotation(classOf[Beetl])
      freemarkerAnnotation.value + suffix
    })
  }
}
