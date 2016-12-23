package shiro


import javax.inject.{Inject, Singleton}

import entities.TUserRepository
import org.apache.shiro.authc._
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import services.Counter

import scala.collection.JavaConversions._

@Singleton
case class RealmService @Inject()(counter: Counter, userRepo: TUserRepository) extends AuthorizingRealm {

  override def getName: String = "MyShiroRealm"

  override def supports(token: AuthenticationToken): Boolean = token.isInstanceOf[UsernamePasswordToken]

  override def doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo = {
    val info = new SimpleAuthorizationInfo()
    try {
      val un = getAvailablePrincipal(principals)
      userRepo.findByUsername(un.toString).roles.map(_.roleName).foreach(info.addRole(_))
    } catch {
      case e: Exception => e.printStackTrace()
    }
    info
  }

  @throws(classOf[Exception])
  override def doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo = {
    val username = token.getPrincipal.toString
    val password = new String(token.getCredentials().asInstanceOf[Array[Char]])
    val user = userRepo.findByUsername(username)
    if (user != null && user.password.equalsIgnoreCase(password)) {
      new SimpleAuthenticationInfo(username, password, getName)
    } else throw new IncorrectCredentialsException("密码错误！！！")
  }
}
