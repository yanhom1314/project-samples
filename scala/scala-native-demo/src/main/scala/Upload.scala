//import scala.sys.process._
object Upload extends App {
  println("Hello, World!")

/**
   val key = "greatbit@2012"
    val mc_url= "http://221.231.154.58:9696"
    val et_url = "http://61.160.183.220/jsts/ebit/start"
    if(args.length == 1){
        val account = args(0)
        val map = s"curl ${mc_url}/ip?un=${account}".!!
                        .split("&").map(_.split("=") match {case Array(k,v) => k -> v}).toMap

        println(map)

        val _ip=map.get("ip")
        if(_ip.isDefined) {
            val c1 = s"curl ${et_url}/${account}/${_ip.get}/down/100?k=${key}".!!
            println(c1)
            val c2 = s"curl ${et_url}/${account}/${_ip.get}/up/10?k=${key}".!!
            println(c2)
        }else println("NOT FOUND IP.")
    }
    else println("Usage: ./upload.sh [account]")
    */
}
