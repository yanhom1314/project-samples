## Akka.io��Scala&Java Actor ģ�Ϳ�

#### Local
+ ȱʡ���������ʹ��actorOf��������Local Actor�������ڵ�ǰJVM�б����ã�

#### Remote
+ server node����server.conf:

	akka {
   	  loglevel = "ERROR"
      actor {
        provider = "akka.remote.RemoteActorRefProvider"  			#�޸�providerΪakka.remote.RemoteActorRefProvider
      }
      remote {
        enabled-transports = ["akka.remote.netty.tcp"]   			#�޸�Զ�̵���ʹ��TCPЭ��
        netty.tcp {
          hostname = "127.0.0.1"                        			#TCP���������������Ͷ˿ڣ�һ���˿���ע����Actorʵ������ͨѶ
          port = 2555	
        }
      }
    }

+ client node����client.conf��

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
	      port = 0                                      			#akka.remote.netty.tcp.portΪ0ʱΪ����˿�
	    }
	  }
    }


+ `system`������`val system = ActorSystem("actorSystemName",ConfigFactory.load("configName"));`
����`configName`Ϊ�����ļ�ǰ׺��������`demo1.conf`��`configName`Ϊ`demo1`��

+ ʹ��Զ�̽ڵ��Actor��

1.Lookup������Զ�̽ڵ��Ѵ����õ�Actor��ʹ��`actorSelection(path)`��
2.Creation������Actor��Զ�̽ڵ㣬ʹ��`actorOf(Props(...), actorName`��


+ Lookup:
���磺

    val selection = context.actorSelection("akka.tcp://actorSystemName@10.0.0.1:2555/user/actorName")

* ·��ģʽ��`akka.<protocol>://<actor system>@<hostname>:<port>/<actor path>`�����磺

    server node����һ��Actor:`val system = ActorSystem("demoActorSystem"); val hi = system.actorOf(Props[HiActor],"hiActor")`��
    ����path��`akka://demoActorSystem/user/hiActor`.

    ���clientҪ�������Actor����path:`akka.tcp://actorSystemName@ip:port/user/actorName`����ȷ��protocl��actorSystemName��ip��port��actorName��Ϣ��
    `val hiActor = context.actorSelection("akka.tcp://demoActorSystem@node1:port/user/hiActor")`

+ Creation��

    val system = ActorSystem("demo1ActorSystem",ConfigFactory.load("demo2"))
    val hiActor = system.actorOf(Props[HiActor],"hiActor") //����name��������deployment�е���Ҫһ�£�

+ ʹ��`Props.create`�������ɷ�ȱʡConstructor������Actor��

    .actorOf(Props.create(classOf[HelloActor],...))


#### Monitor 

+ ʹ��context.watch(actor)��أ���actor dead��ᷢ��һ��Teminated(actor)����Ϣ��context����Actor����recevie�д����ɡ�
+ �Զ���ʱ���½���Actor��MonitorStatus

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

