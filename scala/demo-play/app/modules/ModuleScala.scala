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
    println("""conf/application.conf:[play.modules.enabled += "modules.ModuleScala"].""")

    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    ctx.getBeanNamesForType(classOf[Repository[_, _]]).map(ctx.getType(_)).foreach {
      case c: Class[Repository[_, _]@unchecked] => bind(c).toInstance(ctx.getBean(c))
    }

    init(ctx.getBean(classOf[TUserRepository]), ctx.getBean(classOf[TRoleRepository]))
  }

  private def init(ur: TUserRepository, rr: TRoleRepository): Unit = {
    val r1 = new TRole()
    r1.id = 1
    r1.roleName = "ROLE_ADMIN"
    rr.save(r1)

    val r2 = new TRole()
    r2.id = 2
    r2.roleName = "ROLE_USER"
    rr.save(r2)

    val user = new TUser()
    user.id = 1
    user.username = "admin"
    user.password = "123456"
    user.address = "nanjing"
    user.age = 12
    user.roles = List(r1, r2)
    ur.save(user)

    println("count:" + ur.count())
    println("count:" + rr.count())
  }
}
