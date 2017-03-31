package com.greatbit.tags.web.beetl

import java.util
import javax.inject.Inject

import com.twitter.finatra.http.marshalling.MessageBodyComponent

case class BeetlBodyComponent @Inject()(data: util.Map[String, Object], template: String) extends MessageBodyComponent
