package com.example.filter

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

class UserFilter extends SimpleFilter[Request, Response] {
  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    UserContext.setUser(request)
    service(request)
  }
}

case class User(id: Long, name: String = null)

object UserContext {
  private val UserField = Request.Schema.newField[User]()

  implicit class UserContextSyntax(val request: Request) extends AnyVal {
    def user: User = request.ctx(UserField)
  }

  private[example] def setUser(request: Request): Unit = {
    val user = User(1L) //Parse user from request headers/cookies/etc.
    request.ctx.update(UserField, user)
  }
}