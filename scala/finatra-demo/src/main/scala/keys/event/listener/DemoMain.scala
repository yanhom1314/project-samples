package keys.event.listener

object DemoMain extends App {
  KeysListener.watch()

  println("Ok")

  Thread.sleep(10000)
}
