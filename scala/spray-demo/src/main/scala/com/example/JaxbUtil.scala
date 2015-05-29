package com.example

import java.io.{StringReader, StringWriter}
import javax.xml.bind.{JAXBContext, Marshaller};

object JaxbUtil {
  /**
   * JavaBean转换成xml
   * @param obj
   * @param encoding
   * @return
   */
  def convertToXml(obj: Any, encoding: String): String = {
    var result: String = null
    try {
      val context = JAXBContext.newInstance(obj.getClass())
      val marshaller = context.createMarshaller()
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
      marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding)

      val writer = new StringWriter()
      marshaller.marshal(obj, writer)
      result = writer.toString()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    result
  }

  def convertToXml(obj: Any): String = {
    convertToXml(obj, "UTF-8")
  }

  /**
   * xml转换成JavaBean
   * @param xml
   * @param c
   * @return
   */
  @SuppressWarnings(Array("unchecked"))
  def convertToJavaBean[T](xml: String, c: Class[T]): T = {
    var t = null.asInstanceOf[T]
    try {
      val context = JAXBContext.newInstance(c)
      val unmarshaller = context.createUnmarshaller()
      t = unmarshaller.unmarshal(new StringReader(xml)).asInstanceOf[T]
    } catch {
      case e: Exception => e.printStackTrace();
    }
    t
  }
}

