package modules

import java.time.Clock

import com.google.inject.AbstractModule
import config.SpringDataJpaConfig
import entities.{TRole, TRoleRepository, TUser, TUserRepository}
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.data.repository.Repository

import scala.collection.JavaConversions._

class ModuleScala extends AbstractModule {
  lazy val ctx = new AnnotationConfigApplicationContext(classOf[SpringDataJpaConfig])

  override def configure() = {
    println("conf/application.conf:[play.modules.enabled += \"modules.ModuleScala\"].")

    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    ctx.getBeanNamesForType(classOf[Repository[_, _]]).map(ctx.getType(_)).foreach {
      case c: Class[Repository[_, _]@unchecked] => bind(c).toInstance(ctx.getBean(c))
    }

    val u = ctx.getBean(classOf[TUserRepository])
    val r = ctx.getBean(classOf[TRoleRepository])

    val role = new TRole()
    role.id = 1
    role.roleName = "ROLE_ADMIN"
    r.save(role)

    val user = new TUser()
    user.id = 1
    user.username = "admin"
    user.password = "123456"
    user.address = "nanjing"
    user.age = 12
    user.roles = List(role)
    u.save(user)

    println("count:" + u.count())
    println("count:" + r.count())
  }
}
