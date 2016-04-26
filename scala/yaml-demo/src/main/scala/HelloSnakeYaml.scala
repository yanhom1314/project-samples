import java.io._
import java.util

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor._


object HelloSnakeYaml {
  def main(args: Array[String]): Unit = {
    val yaml = new Yaml()
    val document = "\n- Hesperiidae\n- Papilionidae\n- Apatelodidae\n- Epiplemidae"
    val list = yaml.load(document).asInstanceOf[util.List[String]]
    println(list)

    val map = yaml.load("hello: 25").asInstanceOf[util.Map[String, Long]]
    println(map)
    println(map.get("hello"))

    val y2 = new Yaml(new Constructor(classOf[beans.Invoice]))
    val invoice = y2.loadAs(new FileInputStream("src/main/resources/test.yml"),classOf[beans.Invoice])
    println(invoice)
    println(invoice.date +":"+invoice.post_url +":"+invoice.billTo.given+":"+invoice.billTo.family+":"+invoice.billTo.address.city)
  }
}
