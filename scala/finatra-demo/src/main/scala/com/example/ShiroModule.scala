package com.example

import javax.inject.Inject

import com.example.jdbc.DbiWrapper
import com.example.shiro.RealmService
import com.google.inject.{Module, Provides, Singleton}
import com.twitter.inject.TwitterModule
import org.apache.shiro.SecurityUtils
import org.apache.shiro.config.IniSecurityManagerFactory
import org.apache.shiro.mgt.RealmSecurityManager
import org.apache.shiro.realm.Realm

/**
  * Created by YaFengLi on 2016/10/31.
  */
object ShiroModule extends TwitterModule {

  override protected[inject] def modules: Seq[Module] = Array(H2Module)

  protected override def configure(): Unit = {
    try {
      logger.error("My First Apache Shiro Application")

      //new DefaultSecurityManager(realms)
      //1.
      //val factory = new IniSecurityManagerFactory("classpath:shiro.ini")
      val factory = new IniSecurityManagerFactory("classpath:shiro_realm.ini")
      //2.
      val securityManager = factory.getInstance()
      if (securityManager.isInstanceOf[RealmSecurityManager]) {
        val rs = securityManager.asInstanceOf[RealmSecurityManager]
        //TODO
        //rs.getRealms.add(new )
      }

      //3.
      SecurityUtils.setSecurityManager(securityManager)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  @Provides
  @Singleton
  @Inject()
  def realm(dbiWrapper: DbiWrapper): Realm = {
    println(s"dbiWrapper:${dbiWrapper}")
    RealmService(dbiWrapper)
  }
}
