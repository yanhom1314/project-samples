package com.example.http

import java.util
import javax.inject.{Inject, Singleton}

import com.example.jdbi.DBIWrapperImpl
import com.example.jdbi.dao.{SomethingRepository, UserRepository}
import com.example.jdbi.mapper.{Something, User}
import com.example.jpa.repo.{AnotherThingRepository, LoginUserRepository, RoleRepository}
import com.example.jpa.{AnotherThing, LoginUser, Role}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.Max

@Singleton
class DbController @Inject()(dbiWrapper: DBIWrapperImpl, anotherThingRepository: AnotherThingRepository, loginUserRepository: LoginUserRepository, roleRepository: RoleRepository) extends Controller {

  get("/db/init") { _: Request =>
    try {
      dbiWrapper.withRepo[SomethingRepository, Unit] { repo =>
        if (repo.count() <= 0) (1 to 10).foreach(i => repo.save(Something(i, s"some:${i}")))
      }
      dbiWrapper.withRepo[UserRepository, Unit] { repo => if (repo.count() <= 0) repo.save(User(1, "test1", "123456", 12, "NanJing")) }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    response.ok.plain("OK DbiWrapper:" + dbiWrapper.toString)
  }

  get("/db/all") { _: Request =>
    dbiWrapper.withRepo[SomethingRepository, util.List[Something]] { repo =>
      repo.findAll()
    }
  }

  get("/db/show/:id") { request: IdRequest =>
    dbiWrapper.withRepo[SomethingRepository, Something](_.findById(request.id))
  }

  get("/jpa/init") { _: Request =>
    try {
      if (anotherThingRepository.count() <= 0) (1 to 10).foreach(i => anotherThingRepository.save(AnotherThing(i, s"jpa:${i}", s"jpa:${i}")))
      if (roleRepository.count() <= 0) {
        val role = Role("ROLE_USER")
        role.id = 1
        roleRepository.save(role)
      }
      if (loginUserRepository.findByUsername("test") == null || loginUserRepository.findByUsername("test").roles.length <= 0) {
        val user = LoginUser("test", "123456", 12, "NanJing")
        user.id = 1
        user.roles = List(roleRepository.findByRoleName("ROLE_USER"))

        loginUserRepository.save(user)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    response.ok.plain("Spring Data Jpa:" + anotherThingRepository)
  }

  get("/jpa/update") { _: Request =>
    if (loginUserRepository.findByUsername("test") == null || loginUserRepository.findByUsername("test").roles.length <= 0) {
      val user = LoginUser("test", "test_123", 12, "NanJing")
      user.roles = List(roleRepository.findByRoleName("ROLE_USER"))
      loginUserRepository.save(user)
    }
    response.ok.plain("Spring Data Jpa:" + loginUserRepository)
  }

  get("/jpa/all") { _: Request =>
    anotherThingRepository.findAll()
  }

  get("/jpa/show/:id") { request: IdRequest =>
    anotherThingRepository.findOne(request.id)
  }
}

case class IdRequest(@Max(9999) @QueryParam id: Long)
