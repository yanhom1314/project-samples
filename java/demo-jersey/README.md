

## 构建

        gradle clean build 
## 运行

        wget -c http://maven.aliyun.com/nexus/content/groups/public/org/eclipse/jetty/jetty-runner/9.3.7.v20160115/jetty-runner-9.3.7.v20160115.jar?spm=0.0.0.0.tvEODe&file=jetty-runner-9.3.7.v20160115.jar
        java -jar jetty-runner-9.3.7.v20160115.jar --port 8081 build/libs/YOUR_PROJECT_DIR.war

## 测试

        curl -i http://127.0.0.1:8081/demo/example/fuck