package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.jdbc.DbiWrapper
import com.example.jdbc.dao.{AnotherQuery, SomethingRepository}
import com.example.jdbc.mapper.Something
import com.example.jpa.{AnotherThing, SomeThing, AnotherThingRepository, SomeThingRepository}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.Max

@Singleton
class DbController @Inject()(dbiWrapper: DbiWrapper, anotherThingRepository: AnotherThingRepository, somethingRepository:SomeThingRepository) extends Controller {
  get("/db/init") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      if (repo.count() < 5) (0 to 5).foreach(i => repo.save(Something(i, s"another:${i}")))
    }
    dbiWrapper.withRepo[SomethingRepository] { repo =>
      if (repo.count() < 10) (6 to 10).foreach(i => repo.save(Something(i, s"some:${i}")))
    }
    response.ok.plain("OK DbiWrapper:" + dbiWrapper.toString)
  }

  get("/db/another/all") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      repo.findAll()
    }
  }
  get("/db/some/all") { request: Request =>
    dbiWrapper.withRepo[SomethingRepository] { repo =>
      repo.findAll()
    }
  }
  get("/db/another/show/:id") { request: IdRequest =>
    dbiWrapper.withRepo[SomethingRepository](_.findById(request.id))
  }

  get("/jpa/init") { request: Request =>
    if (anotherThingRepository.count() <= 0) (0 to 5).foreach(i => anotherThingRepository.save(AnotherThing(i, s"jpa:${i}", s"jpa:${i}")))
    if (somethingRepository.count() <= 0) (0 to 5).foreach(i => somethingRepository.save(SomeThing(i, s"jpa:${i}", s"jpa:${i}")))
    response.ok.plain("Spring Data Jpa:" + anotherThingRepository)
  }

  get("/jpa/another/find") { request: IdRequest =>
    anotherThingRepository.findOne(request.id)
  }

  get("/jpa/another/all") { request: Request =>
    anotherThingRepository.findAll()
  }

  get("/jpa/some/find") { request: IdRequest =>
    somethingRepository.findOne(request.id)
  }

  get("/jpa/some/all") { request: Request =>
    somethingRepository.findAll()
  }
}

case class IdRequest(@Max(9999) @QueryParam id: Long)
