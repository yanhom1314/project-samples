#### 构建

        sbt
        >assembly

#### Data测试                         
+ 单机：`spark-submit --master local[6] --class demo.SimpleApp spark-demo-x.y.z.jar /tmp/test.log`               
+ 分布式: `spark-submit --master spark://172.16.9.43:7077 --class demo.SimpleApp spark-demo-x.y.z.jar hdfs://hadoop-master:9000/test.log`

#### Streaming测试        
+ 单机：`spark-submit --master local[4] --class demo.StreamingApp spark-demo-x.y.z.jar localhost 9999`               
+ 分布式: `spark-submit --master spark://172.16.9.43:7077 --class demo.StreamingApp spark-demo-x.y.z.jar localhost 9999`