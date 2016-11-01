package com.example.http

import javax.inject.{Inject, Singleton}
import javax.naming.AuthenticationException

import com.example.filter.ShiroFilter
import com.example.freemarker.Freemarker
import com.example.service.ExampleService
import com.twitter.finagle.http.{Request, Status}
import com.twitter.finatra.http.Controller
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.{IncorrectCredentialsException, LockedAccountException, UnknownAccountException, UsernamePasswordToken}
import org.apache.shiro.subject.Subject

@Singleton
class SecurityController @Inject()(service: ExampleService) extends Controller {

  get("/login") { request: Request =>
    LoginView()
  }

  post("/login") { request: Request =>
    val cu = SecurityUtils.getSubject

    val username = request.params.get("username").getOrElse("guest")
    val password = request.params.get("password").getOrElse("guest")
    val remember = request.params.get("remember").getOrElse(false.toString).toBoolean

    val token = new UsernamePasswordToken(username, password)
    token.setRememberMe(remember)

    request.response.statusCode = Status.Unauthorized.code
    try {
      cu.login(token)
      cu.getSession.touch()
      response.ok.plain(s"Current User:${cu.getPrincipal} role:[guest:${cu.hasRole("guest")}] [admin:${cu.hasRole("admin")}]")
    } catch {
      case e: UnknownAccountException => response.unauthorized
      case e: IncorrectCredentialsException => response.unauthorized
      case e: LockedAccountException => response.unauthorized
      case e: AuthenticationException => response.unauthorized
    }
  }

  filter[ShiroFilter].get("/info") { request: Request =>
    val cu = SecurityUtils.getSubject
    InfoView(cu)
  }

  get("/logout") { request: Request =>
    val cu = SecurityUtils.getSubject
    cu.logout()
    response.ok.plain(s"Current User:${cu.getPrincipal} is LOGOUT!!!")
  }

  filter[ShiroFilter].get("/console") { request: Request =>
    val cu = SecurityUtils.getSubject
    if (cu.isAuthenticated) ManageIndexView(cu)
    else LoginView()
  }
}

@Freemarker("info")
case class InfoView(user: Subject = null)

@Freemarker("login")
case class LoginView(user: Subject = null, error: String = null, success: String = null)

@Freemarker("manage/index")
case class ManageIndexView(user: Subject)
