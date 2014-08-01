## Akka.io：Scala&Java Actor 模型库

#### Remote
+ node1配置demo1.conf:

		akka {
 		 loglevel = "ERROR"
      actor {
        provider = "akka.remote.RemoteActorRefProvider"
      }
      remote {
        enabled-transports = ["akka.remote.netty.tcp"]
        netty.tcp {
          hostname = "127.0.0.1"
          port = 2552	
        }
      }
    }

+ node2配置demo2.conf：

    akka {
      loglevel = "ERROR"
      actor {
        provider = "akka.remote.RemoteActorRefProvider"
        deployment {         
          /hiActor {
            remote = "akka.tcp://actorSystemName@127.0.0.1:2552"
          }
        }
      }
    }

* akka.remote.netty.tcp.port为0时为随机端口

+ `system`创建：`val system = ActorSystem("actorSystemName",ConfigFactory.load("configName"));`
其中`configName`为配置文件前缀名，比如`demo1.conf`的`configName`为`demo1`。

+ 使用远程节点的Actor：

1.Lookup：检索远程节点已创建好的Actor，使用`actorSelection(path)`。
2.Creation：创建Actor在远程节点，使用`actorOf(Props(...), actorName`。


+ Lookup:
例如：

    val selection = context.actorSelection("akka.tcp://actorSystemName@10.0.0.1:2552/user/actorName")

* 路径模式：`akka.<protocol>://<actor system>@<hostname>:<port>/<actor path>`，例如：

    node1创建一个Actor:`val system = ActorSystem("demoActorSystem"); val hi = system.actorOf(Props[HiActor],"hiActor")`，
    则其path：`akka://demoActorSystem/user/hiActor`.

    如果node2要调用这个Actor，则path:`akka.tcp://demoActorSystem@node1:port/user/hiActor`，需确定protocl、ip、port信息；
    `val hiActor = context.actorSelection("akka.tcp://demoActorSystem@node1:port/user/hiActor")`


+ Creation：

    val system = ActorSystem("demo1ActorSystem",ConfigFactory.load("demo2"))
    val hiActor = system.actorOf(Props[HiActor],"hiActor") //其中name与配置中deployment中的名要一致；

