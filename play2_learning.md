##Play Frameworke 2.4+

#### HTTP Routing
+ `GET /client/:id             controllers.Home.show(id:Long)`中`:`表示正则表达式`[^\]+`而`id`表示参数名。
+ `GET /client/$id<[0-9]+>     controllers.Home.show(id:Long ?= 99)`
+ `GET /client                 controllers.Home.show(id:Long ?= 99)`


#### 定制根路径
在conf/application.conf中添加play.http.context属性:`play.http.context="/demo"`

则conf/routes中所有映射均自动添加/demo前缀。

#### 表单定制
+ 创建`app\custom`目录，然后在此目录下创建`input.scala.html` Template文件，编译后会生成`custom.html.input`类；

		@(elements: helper.FieldElements)

		<div class="@if(elements.hasErrors) {error}">
			<label for="@elements.id">@elements.label</label>
			<div class="input">
				@elements.input
				<span class="errors">@elements.errors.mkString(", ")</span>
				<span class="help">@elements.infos.mkString(", ")</span>
			</div>
		</div>

+ 创建`MyHelper.scala`:

		object MyHelpers {

		  import views.html.helper.FieldConstructor

		  implicit val myFields = FieldConstructor(custom.html.input.f)
		}

+ 在需要使用模版中：

		@import MyHelpers._

		@helper.form(action = routes.Wizard.form(), 'id -> "form_1") {
			@helper.inputText(userForm("name"), '_id -> "name", '_showConstraints -> false)
			@helper.inputText(userForm("age"), '_id -> "age", '_showConstraints -> false)
			<button type="submit">提交</button>
		}

#### 集成Slick


####使用toJson(t:T)
类型T需定义reads和writes方法，使用implicit。
object Hellos {
  implicit object HelloFormats extends Format[Hello] {
    def writes(obj: Hello) = JsObject(List("name" -> JsString(obj.name), "age" -> JsNumber(obj.age)))

    def reads(json: JsValue) = JsSuccess(Hello((json \ "name").asOpt[String].getOrElse(""), (json \ "age").asOpt[Int].getOrElse(-1))))
    //在play 2.0.x版本中直接返回
    //def reads(json: JsValue) = Hello((json \ "name").asOpt[String].getOrElse(""), (json \ "age").asOpt[Int].getOrElse(-1))
  }
}
在Action中可以parse或者generate符合json格式的数据
def json = Action(parse.json) { implicit request =>
	val json=request.body
	val user=Json.fromJson[User](json).get
	val userJson=Json.toJson(user)
	
}
/*返回application/json格式的内容*/
def json= Action {
	//val hello = Spring.getBeanOfType(classOf[Hello]) // spring		
	val hello = Hello("#What the fucking hell!#",999)    
    Ok(toJson(hello))
}
处理application/json请求可以使用可以处理Anycontent的Action也可以使用Action(parse.json)，建议使用第二种。
def json = Action {
  request =>
    request.body.asJson.map {
      json =>
        val hello = json.as[Hello]
        println("###" + hello.toString)
        (json \ "name").asOpt[String].map {
          name =>
            Ok("Hello" + name)
        }.getOrElse {
          BadRequest("UNKOWN format request")
        }
    }.getOrElse {
      BadRequest("UNKOWN format request")
    }
}

def json2 = Action(parse.json) {
  request =>
    val hello = request.body.as
    println("###" + hello.toString)
    (request.body \ "name").asOpt[String].map {
      name =>
        Ok("Hello" + name)
    }.getOrElse {
      BadRequest("UNKOWN format request")
    }
}

HTTP编程

Actions、Controllers、Results
Controller是一个产生Action的单例对象(object)，而Action是处理一个请求的具体逻辑，Result是处理后返回的结果。
Action提供的apply方法可以接受一个implicit request参数，或者接受一个BodyParser参数和一个implicit request参数

package controllers

import play.api.mvc._

object Application extends Controller{
	Action { request =>
		Ok("Hello "+request)
	}
	Action { implicit request =>
		Ok("Hello "+request)
	}
	Action(parse.json) { implicit request =>
		Ok("Hello "+request)
	}
}
Result对应HTTP的各种响应状态
val ok = Ok("Hello world!")
val notFound = NotFound
val pageNotFound = NotFound(<h1>Page not found</h1>)
val badRequest = BadRequest(views.html.form(formWithErrors))
val oops = InternalServerError("Oops")
val anyStatus = Status(488)("Strange response type")
对于Redirect可以使用
def index = Action {
	Redirect("/user/home")
	//Redirect("/user/home", status = MOVED_PERMANENTLY)
}
修改内容类型、头、Cookie，Flash与Session。
Ok("Hello").as(HTML).withHeaders(CACHE_CONTROL -> "max-age=3600").withCookies(Cookie("key", "val")).flashing("key" -> "val").withSession(session + ("key" -> "val"))


BodyParser处理
缺省类型是BodyParser[AnyContent]， 检查Content-Type，以决定处理为何种类型的值：
text/plain:String
application/json:JsValue
text/xml:NodeSeq
application/form-url-encoded:Map[String,Seq[String]]
multipart/form-data:MultipartFormData[TemporaryFile]
也可以指定BodyParser
def save = Action(parse.text) { request => 
   Ok("Got: " + request.body) 
} 
##创建自己的BodyParser##TODO

Action组合
仅定义一个Action方法
def LoggingAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
  Action {
    request =>
      Logger.info("Calling action")
      f(request)
  }
}
def LoggingAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
  Action(bp) {
    request =>
      f(request)
  }
}
使用
def index = LoggingAction { request =>
  Ok("Hello World")    
}
def index2 = LoggingAction(parse.json) {
  request =>
    Ok("Hello World")
}
封装现有的Action
case class Logging[A] (action:Action[A]) extends Action[A] {
  def apply(request: Request[A]): Result = {
    Logger.info("Calling action")
    action(request)
  }
  
  lazy val parser = action.parser
}
def index = Logging { 
  Action { 
    Ok("Hello World")
  }
}

Http编程
大数据发送
对于小数据量数据的响应，我们不需要设置http header的Content-Length，Play会自动计算，但是当响应的数据量很大时，需要自行计算Content-Length。
例如，传输一个PDF文件：

def down = Action {
  val file = new File("pdf.path")
  val fileContent: Enumerator[Array[Byte]] = Enumerator.fromFile(file)
  SimpleResult(
    header = ResponseHeader(200, Map(CONTENT_LENGTH -> file.length.toString)),
    body = fileContent
  )
}
Play提供了一个便利的处理文件方法：
def down = Action {
	Ok.sendFile(new File("file.path"))
}
也可以使用具体的信息调用：
def down = Action {
	Ok.sendFile(content = new File("file.path"),fileName= _ => "save.file.name")
}


Comet Sockets
comet是一种服务器端向客户端推送下数据的技术，是对目前由客户端发起请求->响应的结构的补充。
lazy val clock: Enumerator[String] = {
  import java.util._
  import java.text._
  val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  Enumerator.fromCallback[String] {    
    () =>    
      //isRun函数为判断是否推送结束
      if (isRun()) {
        //每一秒返回一个Option[String]
        Promise.timeout(Some(df.format(new Date)), 1000 milliseconds)
      }
      else {
        Akka.future {
        	//推送结束
          None
        }
      }
  }
}

def comet = Action {
	Ok.stream(clock &> Comet(callback = "parent.cometMessage"))
}
在routes中定义：
GET    /comet     controllers.Application.comet
在view中引用
<script type="text/javascript">
    var cometMessage = function (event) {
        console.log('Received event: ' + event)
</script>
<iframe src="@routes.Application.comet"></iframe>

WebSocket
HTML5中定义了WebSocket规范，可以使用WebSocket是来代替Comet技术，目前chrome，firefox，oppra都支持websocket，但是IE目前还不支持。

####POST方法参数获取
*在Play 2.x中通过POST方法提交的参数没有放在queryString中，不能在`routes`文件中配置参数名对应获取*
+ 获取POST参数的方法两种：
+ 1.使用表单`bindFromRequest`绑定验证:

    val postForm = Form(mapping("id" -> text)(PostParam.apply)(PostParam.unapply))

    def post = Action {
      implicit request =>
        postForm.bindFromRequest.fold(error => BadRequest,success => Ok)
    }
+ 2.使用`request.body.asFormUrlEncoded`获取:

    def post = Action {
      implicit request =>
        request.body.asFormUrlEncoded.foreach(println _)
    }

####BodyParser的长度
+ 1.可以在`application.conf`中添加：`parsers.text.maxLength = 4096K`
+ 2.可以在Controller中定义：

    def index = Action(parsers.text.maxLength(1024 * 1000)) {
      Ok()
    }

####MultipartForm
+ 1.可以定义`Action(parse.multipartFormData)`
+ 2.可以使用`request.body.asMultipartFormData`
