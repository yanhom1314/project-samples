package services

import javax.inject.Singleton

import config.AppConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@Singleton
class SpringContextLoader {
  val ctx = new AnnotationConfigApplicationContext(classOf[AppConfig])

  def bean[T](c: Class[T]): T = ctx.getBean(c)

}
