## Akka.io：Scala&Java Actor 模型库

#### Local
+ 缺省配置情况，使用actorOf创建的是Local Actor，仅能在当前JVM中被调用；

#### Remote
+ server node配置server.conf:

	akka {
   	  loglevel = "ERROR"
      actor {
        provider = "akka.remote.RemoteActorRefProvider"  			#修改provider为akka.remote.RemoteActorRefProvider
      }
      remote {
        enabled-transports = ["akka.remote.netty.tcp"]   			#修改远程调用使用TCP协议
        netty.tcp {
          hostname = "127.0.0.1"                        			#TCP服务侦听的主机和端口，一个端口上注册多个Actor实例互相通讯
          port = 2555	
        }
      }
    }

+ client node配置client.conf：

    akka {
      loglevel = "ERROR"
      actor {
        provider = "akka.remote.RemoteActorRefProvider"
        deployment {         
          /hiActor {
            remote = "akka.tcp://actorSystemName@127.0.0.1:2555"
          }
		  /helloActor {
            remote = "akka.tcp://actorSystemName@127.0.0.1:2555"
          }
        }
      }
	  remote {
    	enabled-transports = ["akka.remote.netty.tcp"]
	    netty.tcp {
    	  hostname = "127.0.0.1"
	      port = 0                                      			#akka.remote.netty.tcp.port为0时为随机端口
	    }
	  }
    }


+ `system`创建：`val system = ActorSystem("actorSystemName",ConfigFactory.load("configName"));`
其中`configName`为配置文件前缀名，比如`demo1.conf`的`configName`为`demo1`。

+ 使用远程节点的Actor：

1.Lookup：检索远程节点已创建好的Actor，使用`actorSelection(path)`。
2.Creation：创建Actor在远程节点，使用`actorOf(Props(...), actorName`。


+ Lookup:
例如：

    val selection = context.actorSelection("akka.tcp://actorSystemName@10.0.0.1:2555/user/actorName")

* 路径模式：`akka.<protocol>://<actor system>@<hostname>:<port>/<actor path>`，例如：

    server node创建一个Actor:`val system = ActorSystem("demoActorSystem"); val hi = system.actorOf(Props[HiActor],"hiActor")`，
    则其path：`akka://demoActorSystem/user/hiActor`.

    如果client要调用这个Actor，则path:`akka.tcp://actorSystemName@ip:port/user/actorName`，需确定protocl、actorSystemName、ip、port、actorName信息；
    `val hiActor = context.actorSelection("akka.tcp://demoActorSystem@node1:port/user/hiActor")`

+ Creation：

    val system = ActorSystem("demo1ActorSystem",ConfigFactory.load("demo2"))
    val hiActor = system.actorOf(Props[HiActor],"hiActor") //其中name与配置中deployment中的名要一致；

+ 使用`Props.create`可以生成非缺省Constructor构建的Actor：

    .actorOf(Props.create(classOf[HelloActor],...))


#### Monitor 

+ 使用context.watch(actor)监控，当actor dead后会发送一条Teminated(actor)的消息给context所属Actor，在recevie中处理即可。
+ 自动定时重新建立Actor的MonitorStatus

import akka.actor._
import ActorContaints._

import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class MonitorStatus extends Actor with ActorLogging {

  import demo.actor.MonitorStatus._

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    //init 
    loadActors(context)
    //scheduler scan dead actor queue
    context.system.scheduler.schedule(5 seconds, 10 seconds) {
      println(s"###scan ACTORS_QUEUE:${DEAD_Q.size} ${DEAD_Q.isEmpty}###")
      while (!DEAD_Q.isEmpty) {
        val actor = DEAD_Q.dequeue()
        if (context != null) {
          context.stop(actor)
          context.unwatch(actor)
          loadActors(context)
        }
      }
    }
  }

  override def receive: Receive = {
    case Terminated(ref) => dead(ref)
    case _ => println("Nothing.")
  }

  private def dead(ref: ActorRef): Unit = {
    println(s"::::dead watch::::actor ame:[${ref.path.name}]")
    DEAD_Q += ref
    ACTOR_M -= ref.path.name
  }
}

object MonitorStatus {
  val ACTOR_M = TrieMap[String, ActorRef]()

  private val DEAD_Q = mutable.Queue[ActorRef]()

  def createActor(name: String, ct: Class[_], context: ActorContext, args: Any*): ActorRef = {
    ACTOR_M += (name -> context.system.actorOf(if (args.size > 0) Props(ct, args) else Props(ct), name))
    println(s"NEW ACTOR---> ${name}")
    ACTOR_M(name)
  }

  def loadActors(context: ActorContext): Unit = {
    if (!ACTOR_M.contains(HI_ACTOR)) context.watch(createActor(HI_ACTOR, classOf[SayHi], context))
    if (!ACTOR_M.contains(HELLO_ACTOR)) context.watch(createActor(HELLO_ACTOR, classOf[SayHello], context))
  }
}

