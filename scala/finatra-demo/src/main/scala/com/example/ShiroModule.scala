package com.example

import javax.inject.{Inject, Singleton}

import com.example.jdbi.DbiWrapper
import com.example.jpa.repo.LoginUserRepository
import com.example.shiro.RealmService
import com.google.inject.Provides
import com.twitter.inject.{Injector, TwitterModule}
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager
import org.apache.shiro.realm.Realm

object ShiroModule extends TwitterModule {
  override def singletonStartup(injector: Injector): Unit = {
    try {
      logger.error("My First Apache Shiro Application")
      val securityManager = new DefaultSecurityManager(injector.instance[Realm])
      SecurityUtils.setSecurityManager(securityManager)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  @Provides
  @Singleton
  @Inject()
  def getRealm(dbiWrapper: DbiWrapper, loginUserRepository: LoginUserRepository): Realm = {
    RealmService(dbiWrapper, loginUserRepository)
  }
}
