import java.io.StringWriter
import java.util

import com.github.mustachejava.DefaultMustacheFactory
import org.scalatest.FunSuite

/**
  * Created by YaFengLi on 2016/6/30.
  */
class TemplateSpec extends FunSuite {
  test("Template") {
    try {
      val list = util.Arrays.asList(
        Item("Item 1", "$19.99", util.Arrays.asList(Feature("New!"), Feature("Awesome!"))),
        Item("Item 2", "$29.99", util.Arrays.asList(Feature("Old."), Feature("Ugly."))))
      val mf = new DefaultMustacheFactory()
      val mustache = mf.compile("template.mustache")
      val writer = new StringWriter()
      mustache.execute(writer, Data(list)).flush()
      println(writer.toString)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

case class Data(items: util.List[Item])

case class Item(name: String, price: String, features: util.List[Feature])

case class Feature(description: String)