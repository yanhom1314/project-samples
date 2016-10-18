import java.time.Clock

import com.google.inject.AbstractModule
import config.SpringDataJpaConfig
import entities.{TAddressRepository, TPersonRepository}
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ModuleScala extends AbstractModule {

  lazy val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

  override def configure() = {
    println("conf/application.conf:[play.modules.enabled += \"ModuleScala\"].")
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    setup(classOf[TPersonRepository])
    setup(classOf[TAddressRepository])
  }

  private def setup[T](c: Class[T]): Unit = {
    bind(c).toInstance(ctx.getBean(c))
  }
}
