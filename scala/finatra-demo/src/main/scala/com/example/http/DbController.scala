package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.jdbc.DbiWrapper
import com.example.jdbc.dao.{AnotherQuery, SomethingRepository}
import com.example.jdbc.mapper.Something
import com.example.jpa.{AnotherThing, AnotherThingRepository}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.Max

@Singleton
class DbController @Inject()(dbiWrapper: DbiWrapper, anotherThingRepository: AnotherThingRepository) extends Controller {
  get("/db/add") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      if (repo.count() < 5) (0 to 5).foreach(i => repo.save(Something(i, s"hello:${i}")))
    }
    response.ok.plain("OK DbiWrapper:" + dbiWrapper.toString)
  }

  get("/db/another/list") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      repo.findAll()
    }
  }
  get("/db/list") { request: Request =>
    dbiWrapper.withRepo[SomethingRepository] { repo =>
      repo.findAll()
    }
  }
  get("/db/show/:id") { request: IdRequest =>
    dbiWrapper.withRepo[SomethingRepository](_.findById(request.id))
  }

  get("/jpa/add") { request: Request =>
    if (anotherThingRepository.count() <= 0) (0 to 5).foreach(i => anotherThingRepository.save(AnotherThing(i, i.toString, i.toString)))
    response.ok.plain("Spring Data Jpa:" + anotherThingRepository)
  }

  get("/jpa/find") { request: IdRequest =>
    anotherThingRepository.findOne(request.id)
  }
  get("/jpa/all") { request: Request =>
    anotherThingRepository.findAll()
  }
}

case class IdRequest(@Max(9999) @QueryParam id: Long)
