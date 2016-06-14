import java.io.{File, FileFilter}
import java.time.Clock

import com.google.inject.AbstractModule
import config.AppConfig
import entities.TPersonRepository
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.collection.mutable

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.
  *
  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    //bind(classOf[ApplicationTimer]).asEagerSingleton
    // Set AtomicCounter as the implementation for Counter.
    //bind(classOf[Counter]).to(classOf[AtomicCounter])
    //Spring Data Jpa
    //bind(classOf[SpringContextLoader]).asEagerSingleton()


    val ctx = new AnnotationConfigApplicationContext(classOf[AppConfig])

    bind(classOf[TPersonRepository]).toInstance(ctx.getBean(classOf[TPersonRepository]))


    val resource = this.getClass.getClassLoader.getResources("entities")
    import scala.collection.JavaConversions._
    resource.toStream.foreach { u =>
      val fn = u.getFile
      println(s"url:${fn}")
      val list = mutable.Buffer[String]()
      loop(fn, list)
      list.foreach(println(_))
    }
  }

  def loop(fn: String, list: mutable.Buffer[String]): Unit = {
    new File(fn).listFiles(new FileFilter {
      override def accept(f: File): Boolean = f.isDirectory || f.getName.endsWith(".class")
    }).foreach { f =>
      if (f.isFile) list += f.getName else loop(f.getAbsolutePath, list)
    }
  }
}
