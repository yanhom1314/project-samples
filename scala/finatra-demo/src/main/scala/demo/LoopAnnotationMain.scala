package demo

/**
  * Created by YaFengLi on 2017/4/16.
  */
object LoopAnnotationMain extends App {
  classOf[Route].getAnnotations.filter(_.isInstanceOf[Get]).foreach(a => println(a.asInstanceOf[Get].value()))

  classOf[Route].getDeclaredMethods.filter(m => m.getAnnotation(classOf[Get]) != null).foreach { m =>
    println(m.getAnnotation(classOf[Get]).value())
  }

  classOf[Route].getAnnotation(classOf[Get]) match {
    case g: Get => println(g.value())
    case null => println("null")
  }
  classOf[Route].getDeclaredAnnotation(classOf[Get]) match {
    case g: Get => println(g.value())
    case null => println("null")
  }
}
