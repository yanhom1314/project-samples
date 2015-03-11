演示Thrift远程调用。
--------------


#### 编辑(.thrift)描述文件
+ `add.thrift`

        namespace java io.lyf.samples.thrift.server  // defines the namespace

        typedef i32 int  //typedefs to get convenient names for your types

        service AdditionService {  // defines the service to add two numbers
                int add(1:int n1, 2:int n2), //defines a method
        }

+ 生成模板文件：`thrift --gen java add.thrift -o src/main/java`