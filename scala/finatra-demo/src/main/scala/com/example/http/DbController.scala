package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.jdbi.DbiWrapper
import com.example.jdbi.dao.{SomethingRepository, UserRepository}
import com.example.jdbi.mapper.{Something, User}
import com.example.jpa.repo.{AnotherThingRepository, LoginUserRepository, RoleRepository}
import com.example.jpa.{AnotherThing, LoginUser, Role}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.Max

@Singleton
class DbController @Inject()(dbiWrapper: DbiWrapper, anotherThingRepository: AnotherThingRepository, loginUserRepository: LoginUserRepository, roleRepository: RoleRepository) extends Controller {

  get("/db/init") { request: Request =>
    dbiWrapper.withRepo[SomethingRepository] { repo =>
      if (repo.count() <= 0) (1 to 10).foreach(i => repo.save(Something(i, s"some:${i}")))
    }
    dbiWrapper.withRepo[UserRepository] { repo => if (repo.count() <= 0) repo.save(User(1, "test", "test_password", 12, "NanJing")) }
    response.ok.plain("OK DbiWrapper:" + dbiWrapper.toString)
  }

  get("/db/all") { request: Request =>
    dbiWrapper.withRepo[SomethingRepository] { repo =>
      repo.findAll()
    }
  }

  get("/db/show/:id") { request: IdRequest =>
    dbiWrapper.withRepo[SomethingRepository](_.findById(request.id))
  }

  get("/jpa/init") { request: Request =>
    if (anotherThingRepository.count() <= 0) (1 to 10).foreach(i => anotherThingRepository.save(AnotherThing(i, s"jpa:${i}", s"jpa:${i}")))
    if (loginUserRepository.count() <= 0) {
      val role = Role("ROLE_USER")
      roleRepository.save(role)
      val user = LoginUser("test", "test_123", 12, "NanJing")
      user.roles.add(role)
      loginUserRepository.save(user)
    }
    response.ok.plain("Spring Data Jpa:" + anotherThingRepository)
  }

  get("/jpa/all") { request: Request =>
    anotherThingRepository.findAll()
  }

  get("/jpa/show/:id") { request: IdRequest =>
    anotherThingRepository.findOne(request.id)
  }
}

case class IdRequest(@Max(9999) @QueryParam id: Long)
