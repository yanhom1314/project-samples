package com.example

import com.twitter.inject.TwitterModule
import org.apache.shiro.SecurityUtils
import org.apache.shiro.config.IniSecurityManagerFactory

/**
  * Created by YaFengLi on 2016/10/31.
  */
object ShiroModule extends TwitterModule {
  protected override def configure(): Unit = {
    try {
      logger.error("My First Apache Shiro Application")
      //1.
      //val factory = new IniSecurityManagerFactory("classpath:shiro.ini")
      val factory = new IniSecurityManagerFactory("classpath:shiro_realm.ini")
      //2.
      val securityManager = factory.getInstance()
      //3.
      SecurityUtils.setSecurityManager(securityManager)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
