#### 构建

        sbt
        >assembly

#### 运行
        
        spark-submit --class demo.SimpleApp spark-demo-x.y.z.jar /demo/txt/file 
        spark-submit --class demo.DomainApp spark-demo-x.y.z.jar /demo/txt/file
        spark-submit --class demo.StreamingApp spark-demo-x.y.z.jar localhost 9999