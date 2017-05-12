package demo

import org.json4s.JsonDSL.WithDouble._
import org.json4s._
import org.json4s.native.JsonMethods._

object HelloJson4s extends App {

  val jv_1 = parse("""{"name":"yafengli@sina.com","age":1,"numbers":[1,2,3,4,5]}""")
  println(jv_1)
  println(compact(render(jv_1)))
  val json = List(1, 2, 3)
  println(compact(render(json)))
  println(compact(render(("name" -> "joe"))))

  println(compact(render(jv_1 \\ "name")))
  println(compact(render(jv_1 \\ "numbers")))

  val json2 = parse(
    """
         { "name": "joe",
           "children": [
             {
               "name": "Mary",
               "age": 5
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """)

  val res_1 = for {
    JObject(child) <- json2
    JField("age", JInt(age)) <- child
  } yield age

  println(res_1)
}
