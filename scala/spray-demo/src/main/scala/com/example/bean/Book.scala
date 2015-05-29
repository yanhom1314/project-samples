package com.example.bean

import java.util.Date
import javax.xml.bind.annotation._

import scala.beans.BeanProperty

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "book", propOrder = Array("author", "calendar", "price", "id"))
case class Book(@BeanProperty @XmlElement(required = true) var author: String, @BeanProperty @XmlElement(required = true) var price: Float, @BeanProperty @XmlElement(required = true) var calendar: Date, @BeanProperty @XmlAttribute var id: Int) {
  def this() = this(null, 0.0f, new Date(), -1)
}
