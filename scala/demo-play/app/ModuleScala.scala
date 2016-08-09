import java.io.{File, FileFilter}
import java.time.Clock

import com.google.inject.AbstractModule
import config.SpringDataJpaConfig
import entities.TPersonRepository
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

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
class ModuleScala extends AbstractModule {

  import scala.collection.JavaConversions._
  import scala.reflect.runtime.universe._

  override def configure() = {
    println("conf/application.conf:[play.modules.enabled += \"ModuleScala\"].")
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

    this.getClass.getClassLoader.getResources("entities").toStream.foreach { u => loop(new File(u.getFile), ctx) }
  }

  def loop(dir: File, ctx: ApplicationContext): Unit = {
    dir.listFiles(new FileFilter {
      override def accept(f: File): Boolean = f.isDirectory || (f.getName.indexOf("Repository") > 0 && f.getName.endsWith(".class"))
    }).foreach { f =>
      if (f.isFile) {
        val cn = s"${dir.getName}.${f.getName.replace(".class", "")}"
        println(cn)
        val ms = runtimeMirror(classOf[ModuleScala].getClassLoader).moduleSymbol(Class.forName(cn))

        //val c = ms.getClass
        val c = Class.forName(cn)
        Package.getPackage("")

        //bind(c).toInstance(ctx.getBean(c))  //TODO error
        if (cn.indexOf(classOf[TPersonRepository].getName) >= 0) {
          val c = classOf[TPersonRepository]
          bind(c).toInstance(ctx.getBean(c))
        }
      }
      else loop(f, ctx)
    }
  }
}
