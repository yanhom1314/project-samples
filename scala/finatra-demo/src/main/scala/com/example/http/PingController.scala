package com.example.http

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.example.filter.AddCookieFilter
import com.example.service.ExampleService
import com.twitter.finagle.http.{Cookie, Request, Response}
import com.twitter.finatra.http.Controller
import com.twitter.util

@Singleton
class PingController @Inject()(service: ExampleService) extends Controller {

  get("/ping") { _: Request =>
    info("ping")
    "pong" + System.currentTimeMillis()
  }

  get("/name") { request: Request =>
    request.headerMap.foreach { t =>
      println(s"${t._1}:${t._2}")
    }
    service.myDo(request)
    response.ok.plain("Bob")
  }

  filter[AddCookieFilter].get("/cookie") { request: Request =>
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(s"Come in /cookie:${format.format(new Date())}")
    val c1 = new Cookie("SESSION_ID", System.currentTimeMillis().toString)
    c1.maxAge = util.Duration.apply(500, TimeUnit.SECONDS)
    val c2 = new Cookie("OPEN_ID", System.currentTimeMillis().toString)
    c2.maxAge = util.Duration.apply(500, TimeUnit.SECONDS)
    val c3 = new Cookie("USER_ID", System.currentTimeMillis().toString)
    c3.maxAge = util.Duration.apply(500, TimeUnit.SECONDS)
    response.ok.plain("Add Cookie OK!").cookie(c1).cookie(c2).cookie(c3)
  }

  get("/trace") { request: Request =>
    val buffer = new StringBuffer()
    buffer.append(request.params.map { t => s"${t._1}:${t._2}" }.mkString("<br/>"))
    buffer.append(request.cookies.map { t => s"${t._1}:${t._2.value}" }.mkString("<br/>"))
    buffer.append(Response(request).cookies.map { t => s"${t._1}:${t._2.value}" }.mkString("<br/>"))

    response.ok.html(buffer.toString)
  }
}
