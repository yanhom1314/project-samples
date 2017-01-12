#### Scala隐式导入
+ `implicit`可以修改`class`、`method`、`value`；

#### Class
+ 使用`implicit`修饰的`Class`必须有且只有一个构造函数参数；

		implicit class StringD(val name:String) {
			def sayHello = s"Hello,I'm $name"
		}

		"YaFengLi".sayHello


#### Method
+ 使用`implicit`修饰的`method`

		class StringE(val name:String) {
			def sayHello=s"Hello,I'm $name"
		}
		implicit final def str2SayHello(name:String) = new StringE(name)

**`implicit class`就是将`implicit method`与`class`整合到了一起，有好处但是也有`class`构造函数写法的限制**


#### Value
+ 使用`implicit`修饰的`value`

		object StringF {
			implicit val name:String = "Hello World!"
		}

		object Main extends App {
			
			def say(implicit name:String="Wahaha",y:Int = 5) {
				(0 to 5).foreach {
					i =>
						println(name)
				}
			}

			import StringF._

			say
			say()
		}

**当调用含有`implicit value parameter`的函数时，不使用括号则使用`implicit value`在本例中使用的是`Hello World!`**
**当调用含有`implicit value parameter`的函数时，使用括号则使用函数定义时的缺省参数值`Wahaha`**
**当`implicit value`的定义在调用之前，可以使用类型推导，例如：`implicit val name = "Hello"`，否则必须定义类型，例如：`implicit val name:String = "Hello"`**
**使用`implicit class method value`，只要`import`导入即可使用**


## Scala的Symbol类
+ Symbol是scala中的一个实例类：

		final case class Symbol private(name: String) {
 			override def toString: String = "’" + name
		} 
