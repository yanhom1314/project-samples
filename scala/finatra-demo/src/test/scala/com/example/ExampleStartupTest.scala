package com.example

import com.google.inject.Stage
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.Test

class ExampleStartupTest extends Test {

  val server = new EmbeddedHttpServer(
    twitterServer = new ExampleServer,
    stage = Stage.PRODUCTION,
    verbose = false)

  "server" in {
    server.assertHealthy()
  }
}
