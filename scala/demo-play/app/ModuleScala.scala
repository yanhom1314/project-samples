import java.time.Clock

import com.google.inject.AbstractModule
import config.SpringDataJpaConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.data.repository.Repository

class ModuleScala extends AbstractModule {
  val PKG_PREFIX_NAME = "entities"
  lazy val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

  override def configure() = {
    println("conf/application.conf:[play.modules.enabled += \"ModuleScala\"].")
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    //setup(classOf[TPersonRepository])
    //setup(classOf[TAddressRepository])

    ctx.getBeanNamesForType(classOf[Repository[_, _]]).map(ctx.getType(_)).foreach {
      case c: Class[Repository[_, _]@unchecked] => bind(c).toInstance(ctx.getBean(c))
    }
  }

  private def setup[T](c: Class[T]): Unit = {
    bind(c).toInstance(ctx.getBean(c))
  }
}
