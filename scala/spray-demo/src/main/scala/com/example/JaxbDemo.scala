package com.example

import java.util.Date

import com.example.bean.Book

object JaxbDemo extends App {

  showMarshaller
  showUnMarshaller

  def showMarshaller() {
    val book = Book("James", 23.45f, new Date(), 100)
    val str = JaxbUtil.convertToXml(book)
    println(str)
  }

  def showUnMarshaller() {
    val str =
      """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
         <book id="100">
            <author>James</author>
            <calendar>2013-03-29T09:25:56.004+08:00</calendar>
            <price_1>23.45</price_1>
         </book>"""
    val book = JaxbUtil.convertToJavaBean(str, classOf[Book])
    println(book)
  }
}
