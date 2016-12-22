package shiro


import javax.inject.{Inject, Singleton}

import entities.TUserRepository
import org.apache.shiro.authc._
import org.apache.shiro.authz.{AuthorizationInfo, SimpleAuthorizationInfo}
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import services.Counter

@Singleton
case class RealmService @Inject()(counter: Counter, userRepo: TUserRepository) extends AuthorizingRealm {

  override def getName: String = "MyRealm"

  override def supports(token: AuthenticationToken): Boolean = token.isInstanceOf[UsernamePasswordToken]

  override def doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo = {
    val info = new SimpleAuthorizationInfo()
    try {
      //设置权限
      val un = getAvailablePrincipal(principals)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    //通过un查找Roles
    //loginUserRepository.findByUsername(un.toString).roles.map(_.roleName).foreach(info.addRole(_))
    info.addRole("ROLE_ADMIN")
    info
  }

  //@throws(classOf[Exception])
  override def doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo = {
    try {
      val username = token.getPrincipal.toString
      val password = new String(token.getCredentials().asInstanceOf[Array[Char]])

      new SimpleAuthenticationInfo(username, password, getName)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        null
    }

    //TODO

    //    val user = loginUserRepository.findByUsername(username)
    //    if (user != null && user.password.equalsIgnoreCase(password)) {
    //      val info = new SimpleAuthenticationInfo(username, password, getName)
    //      info
    //    } else throw new IncorrectCredentialsException("密码错误！！！")
  }
}
