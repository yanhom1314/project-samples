## AutoReload
+ `build.gradle`的`dependencies`增加`compile("org.springframework.boot:spring-boot-devtools")`

+ 运行两个终端:
* `gradle -Dlocal.doc.root=src\main\resources bootRun`
* `gradle build --continuous`


