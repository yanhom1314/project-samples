package demo

@Get("/")
case class Route(name: String) {
  @Get("hello")
  def hello(id: Long) = {
    "<h1>Hello World!</h1"
  }
}
