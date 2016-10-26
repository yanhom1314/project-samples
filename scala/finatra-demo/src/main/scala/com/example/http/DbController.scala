package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.jdbc.mapper.Something
import com.example.jdbc.DbiWrapper
import com.example.jdbc.dao.AnotherQuery
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class DbController @Inject()(dbiWrapper: DbiWrapper) extends Controller {
  get("/db/add") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      if (repo.count() < 5) (0 to 5).foreach(i => repo.save(Something(i, s"hello:${i}")))
    }
    response.ok.plain("OK DbiWrapper:" + dbiWrapper.toString)
  }

  get("/db/list") { request: Request =>
    dbiWrapper.withRepo[AnotherQuery] { repo =>
      repo.findAll()
    }
  }
}
