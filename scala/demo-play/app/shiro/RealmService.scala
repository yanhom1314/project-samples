package shiro


import javax.inject.{Inject, Singleton}

import entities.repo.TUserRepository
import org.apache.shiro.authc._
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import services.Counter

import scala.collection.JavaConverters._

@Singleton
case class RealmService @Inject()(counter: Counter, userRepo: TUserRepository) extends AuthorizingRealm {

  override def getName: String = "MyShiroRealm"

  override def supports(token: AuthenticationToken): Boolean = token.isInstanceOf[UsernamePasswordToken]

  override def doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo = {
    val info = new SimpleAuthorizationInfo()
    try {
      val un = getAvailablePrincipal(principals)
      userRepo.findByUsername(un.toString).roles.asScala.map(_.roleName).foreach(info.addRole(_))
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
    System.out.println(s"2:username:${username} password:${password}")
    System.out.println(s"2:username:${user.password} ${user.password.equalsIgnoreCase(password)}")
    if (user != null && user.password.equalsIgnoreCase(password)) {
      new SimpleAuthenticationInfo(username, password, getName)
    } else throw new IncorrectCredentialsException("密码错误！！！")
  }
}
