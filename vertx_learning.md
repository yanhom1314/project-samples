## Vert.x
+ (Vert.x)[http://vertx.io]是一个异步应用程序开发框架，可用于开发异步、可伸缩、高并发的Web应用.目的在于为JVM提供一个Node.js的替代方案.

#### Install
+ `unzip vertx-x.y.z.zip`
+ `tar zxf vertx-x.y.z.tgz`


#### Scala Support
+ 编辑`$VERTX_HOME/conf/langs.properties`增加以下内容：
    
    scala=io.vertx~lang-scala~0.3.0-SNAPSHOT:org.vertx.scala.platform.impl.ScalaVerticleFactory
    .scala=scala

#### Run It
#### Script
+ Scala:`vertx run Server.scala`
+ Server.scala

		vertx.createHttpServer.requestHandler { req: HttpServerRequest =>
  			req.response.end("This is a Verticle script")
		}.listen(8080)

#### Class it
+ `vertx run scala:Server`
+ Server.scala

		import org.vertx.scala.core.http.HttpServerRequest
		import org.vertx.scala.platform.Verticle

		class Server extends Verticle {

  			override def start(): Unit = {
    			vertx.createHttpServer.requestHandler {
      				req: HttpServerRequest =>
        			req.response.end("This is a Verticle script")
    			}.listen(8080)
  			}
		}

#### Library it
+ `vertx run scala:Server -cp ".:demo-x.y.z.jar"`		