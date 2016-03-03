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

#### Class与ClassLoader的getResourceAsStream区别
+ `Class`有相对路径与绝对路径(以`/`开头)，例如：

        com
           |-github
                |-demo
                |    |-A.class
                |    |-1.txt
                |-B.class
                |-2.txt


在这样的编译输出目录下，文件"1.txt"的绝对路径名为："/com/github/demo/1.txt" 文件"2.txt"的绝对路径名为："/com/github/2.txt" 
如同getResourceAsStream方法文档上的解释：以/开头时是绝对路径，与类路径不同的是路径上的“."换成了"/"。

接下着我们看相对路径！

首先我们必须明确“相对”路径总是指相对于当前class的路径！

例如，如果我们使用

InputStream is= A.class.getResourceAsStream("1.txt")；

路径不是以/开头，说明这是一个相对路径，相对的是A.class这个文件，所以，这里的“1.txt”所指的正确位置是与A.class处于同一目录下的1.txt文件，这一文件是存在的，所引不会报错。

如果我们按相对路径的方式通过A去加载2.txt，则路径应该这样描述：

InputStream is= A.class.getResourceAsStream("../2.txt")；   

是的，用“.."表示上一级目录。

如果我们按相对路径的方式通过B去加载1.txt，则路径应该这样描述：   

InputStream is= B.class.getResourceAsStream("demo/1.txt")；   

注意！demo前是没有/的，这是一个相对路径，是相对于B所在包下的demo子包里的1.txt文件，所以这个路径也是正确无误的！

关于ClassLoader.getResourceAsStream

class.getResourceAsStream最终调用是ClassLoader.getResourceAsStream

只是在这之前对参数进行了调整。如果参数已/开头，则去除/，否则把当前类的包名加在参数的前面。

在使用ClassLoader.getResourceAsStream时，路径直接使用相对于classpath的绝对路径,并且不能已 / 开头。

InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream("com/github/demo/1.txt");
