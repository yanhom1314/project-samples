package finatra.greatbit.freemarker

import java.util.concurrent.ConcurrentHashMap

import finatra.views.freemarker.Freemarker

import scala.collection.JavaConverters._

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
      val freemarkerAnnotation = viewObj.getClass.getAnnotation(classOf[Freemarker])
      freemarkerAnnotation.value + ".ftl"
    })
  }
}
