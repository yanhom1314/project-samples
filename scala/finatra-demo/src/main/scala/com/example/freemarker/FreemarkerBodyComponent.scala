package com.example.freemarker

import javax.inject.Inject

import com.twitter.finatra.http.marshalling.MessageBodyComponent

/**
  * Created by LYF on 2016/10/23.
  */
}

case class FreemarkerBodyComponent @Inject()(data: Any,
                                             template: String) extends MessageBodyComponent