package demo.oauth2.http

import javax.inject.Singleton

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class HomeController extends Controller {
  get("/") { _: Request =>
    response.ok.html("<h1>Hello World!</h1>")
  }
}
