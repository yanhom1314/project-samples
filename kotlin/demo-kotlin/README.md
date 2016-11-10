#### FatJar
+ `build.gradle`:
   mainClassName = "demo.HelloKt"
   jar {
     manifest { 
       attributes "Main-Class": "$mainClassName"
     }  

     from {
       configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
    }
  }
  //如果不影响jar，可以使用下面的任务定义
  task fatJar(type: Jar) {
    baseName = project.name + '-all'
    manifest {
      attributes "Main-Class": "$mainClassName" 
    }
    from { configurations.compile.collect { it.isDirectory() ? it : zipTree(it) } }
    with jar
  }

#### Build & Run
+ gradle clean FatJar
