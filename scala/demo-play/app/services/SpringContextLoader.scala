package services

import javax.inject.Singleton

import config.SpringDataJpaConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@Singleton
class SpringContextLoader {
  val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

  def bean[T](c: Class[T]): T = ctx.getBean(c)

}
