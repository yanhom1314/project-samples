## 测试

#### 部署安装
+ 编译：
        
        activator
        >project demo_2
        >package
        >project demo_1
        >package
        >project root
        >distZip
        
+ 安装： 
        
        cp conf/* $AKKA_HOME/config/
        cp target/universal/stage/lib/*.jar $AKKA_HOME/deploy/
             
#### 运行测试

        bin/akka demo.Server
        bin/akka demo.Client           

