import java.io.FileInputStream

import beans.Invoice
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

object HelloJacksonYaml {
  def main(args: Array[String]): Unit = {
    val mapper = new ObjectMapper(new YAMLFactory())

    val invoice = mapper.readValue(new FileInputStream("src/main/resources/test.yml"), classOf[Invoice])

    println(invoice)
    println(invoice.date + ":" + invoice.post_url)
  }
}
