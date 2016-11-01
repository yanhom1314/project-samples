package com.example

import com.twitter.inject.{Injector, TwitterModule}
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager
import org.apache.shiro.realm.Realm

/**
  * Created by YaFengLi on 2016/10/31.
  */
object ShiroModule extends TwitterModule {
  override def singletonStartup(injector: Injector): Unit = {
    try {
      logger.error("My First Apache Shiro Application")
      //
      //1.
      //val factory = new IniSecurityManagerFactory("classpath:shiro.ini")
      //val factory = new IniSecurityManagerFactory("classpath:shiro_realm.ini")
      //2.
      //val securityManager = factory.getInstance()
      val realm = injector.instance[Realm]
      println(realm)
      val securityManager = new DefaultSecurityManager(realm)
      //3.
      SecurityUtils.setSecurityManager(securityManager)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
