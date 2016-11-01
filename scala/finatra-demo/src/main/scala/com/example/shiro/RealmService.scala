package com.example.shiro

import com.example.jdbi.DbiWrapper
import com.example.jpa.repo.LoginUserRepository
import org.apache.shiro.authc._
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

case class RealmService(dbiWrapper: DbiWrapper, loginUserRepository: LoginUserRepository) extends AuthorizingRealm {

  override def getName: String = "OwnRealm"

  override def supports(token: AuthenticationToken): Boolean = token.isInstanceOf[UsernamePasswordToken]

  override def doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo = {
    val un = getAvailablePrincipal(principals)
    //通过un查找Roles

    val info = new SimpleAuthorizationInfo()
    //设置权限
    info.addRole("guest")
    info.addRole("test")

    info
  }

  @throws(classOf[Exception])
  override def doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo = {
    val username = token.getPrincipal.toString
    val password = new String(token.getCredentials().asInstanceOf[Array[Char]])
    println(s"dbiWrapper:${dbiWrapper} loginUserRepository:${loginUserRepository}")
    println(s"username:${username} password:${password}")
    if (username.equalsIgnoreCase("test") && password.equalsIgnoreCase("test_1")) {
      val info = new SimpleAuthenticationInfo(username, password, getName)
      info
    } else throw new IncorrectCredentialsException("密码错误！！！")
  }
}
