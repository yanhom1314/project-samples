#### 构建

        sbt
        >assembly

#### Data测试
        
        spark-submit --class demo.SimpleApp spark-demo-x.y.z.jar /demo/file         

#### Streaming测试        
        spark-submit --class demo.StreamingApp spark-demo-x.y.z.jar localhost 9999