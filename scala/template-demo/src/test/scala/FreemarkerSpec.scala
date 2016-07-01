import java.io.StringWriter

import com.example.Hello
import freemarker.template.{Configuration, TemplateExceptionHandler}
import org.scalatest._

class FreemarkerSpec extends FlatSpec with Matchers {
  "Hello" should "have tests" in {
    import scala.collection.JavaConverters._
    val cfg = new Configuration(Configuration.VERSION_2_3_24)
    cfg.setClassLoaderForTemplateLoading(classOf[Hello].getClassLoader, "")
    cfg.setDefaultEncoding("UTF-8")
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    cfg.setLogTemplateExceptions(false)


    val template = cfg.getTemplate("freemarker/hello.ftl")
    val map = Map("items" -> List("1", "2", "3").asJava).asJava

    //    val map = new util.HashMap[String,util.List[String]]()
    //    map.put("items",new util.ArrayList[String]())
    //    map.get("items").addAll(util.Arrays.asList("1","2","3"))

    val writer = new StringWriter()
    template.process(map, writer)
    println(writer.toString)
  }
}
