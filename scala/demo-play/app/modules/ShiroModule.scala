package modules

import com.google.inject.{AbstractModule, Injector, Provides}
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager
import org.apache.shiro.realm.Realm
import org.apache.shiro.session.mgt.DefaultSessionManager
import play.api.Logger
import shiro.RealmService

class ShiroModule extends AbstractModule {
  override def configure(): Unit = {
    try {
      Logger.error("My First Apache Shiro Application")
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  @Provides
  def getRealm(injector: Injector): Realm = {
    val realm = injector.getInstance(classOf[RealmService])
    println(s"##################:${realm}")
    val securityManager = new DefaultSecurityManager(realm)

    val sessionManager = new DefaultSessionManager()
    sessionManager.setGlobalSessionTimeout(10000L)
    securityManager.setSessionManager(sessionManager)
    SecurityUtils.setSecurityManager(securityManager)
    realm
  }
}
