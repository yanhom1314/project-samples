## 测试


#### actorOf与actorSelection区别与使用
+ `actorOf`为创建一个`Actor`并创建引用`ActorRef`，建议`context.watch`创建的`Actor`，否则当`Actor`创建不成功时，无法会完成期望的消息传输;
+ `actorSelection`不创建Actor，仅创建ActorSelection，ActorSelection为Actor的Pointer，当Actor可用时ask与tell方法可用；
+ 在单JVM中，建议使用`actorOf`，如果是多JVM结构中，建议每一个JVM创建自己的`Actor`，使用`actorSelection`调用其他JVM的`Actor`；

#### 部署安装
+ 编译：
        
        sbt
        >project server
        >package
        >project client
        >package
        >project root
        >distZip
        
+ 安装： 
        
        cp conf/* $AKKA_HOME/config/
        cp target/universal/stage/lib/*.jar $AKKA_HOME/deploy/
             
#### 运行测试

        bin/akka demo.Server
        bin/akka demo.Client           

