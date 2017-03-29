package finatra.greatbit.module

import javax.inject.{Inject, Singleton}

import com.example.jdbi.DBIWrapperImpl
import com.example.jpa.repo.{LoginUserRepository, RoleRepository}
import com.example.shiro.RealmService
import com.google.inject.Provides
import com.twitter.inject.{Injector, TwitterModule}
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.session.mgt.DefaultSessionManager

object ShiroModule extends TwitterModule {
  override def singletonStartup(injector: Injector): Unit = {
    try {
      logger.error("My First Apache Shiro Application")
      val securityManager = new DefaultSecurityManager(injector.instance[Realm])

      val sessionManager = new DefaultSessionManager()
      sessionManager.setGlobalSessionTimeout(10000L)
      securityManager.setSessionManager(sessionManager)
      SecurityUtils.setSecurityManager(securityManager)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  @Provides
  @Singleton
  @Inject()
  def getRealm(dbiWrapper: DBIWrapperImpl, loginUserRepository: LoginUserRepository, roleRepository: RoleRepository): Realm = {
    RealmService(dbiWrapper, loginUserRepository, roleRepository)
  }
}
