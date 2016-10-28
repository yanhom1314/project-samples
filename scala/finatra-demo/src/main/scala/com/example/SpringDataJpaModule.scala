package com.example

import com.example.spring.SpringDataJpaConfig
import com.twitter.inject.TwitterModule
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.data.repository.Repository

/**
  * Created by YaFengLi on 2016/10/27.
  */
object SpringDataJpaModule extends TwitterModule {
  lazy val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

  protected override def configure(): Unit = {
    println("repositories:" + ctx.getBeanNamesForType(classOf[Repository[_, _]]))
    ctx.getBeanNamesForType(classOf[Repository[_, _]]).map(ctx.getType(_)).foreach {
      case c: Class[Repository[_, _]@unchecked] => bind(c).toInstance(ctx.getBean(c))
    }
  }
}