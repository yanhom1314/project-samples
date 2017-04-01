package finatra.greatbit.beetl

import java.io.{ByteArrayOutputStream, OutputStreamWriter, StringWriter}
import java.nio.charset.StandardCharsets
import java.util
import javax.inject.{Inject, Singleton}

import com.twitter.io.Buf

@Singleton
class BeetlService @Inject()(factory: BeetlConfigurationFactory) {
  private[beetl] def createBuffer(templateName: String, obj: util.Map[String, Any]): Buf = {
    val outputStream = new ByteArrayOutputStream(1024)
    val writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
    try {
      val template = factory.groupTemplate.getTemplate(templateName)
      template.binding(obj)
      template.renderTo(writer)
    } catch {
      case e: Exception => e.printStackTrace()
    }
    finally {
      writer.close()
    }

    Buf.ByteArray.Owned(outputStream.toByteArray)
  }

  def createString(templateName: String, obj: util.Map[String, Any]): String = {
    val writer = new StringWriter()
    try {
      val template = factory.groupTemplate.getTemplate(templateName)
      template.binding(obj)
      template.renderTo(writer)
    } catch {
      case e: Exception => e.printStackTrace()
    }

    writer.toString
  }
}
