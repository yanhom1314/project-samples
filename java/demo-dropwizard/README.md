#### Build

    gradle -q fatJar

#### Run

    java -jar build\libs\demo-dropwizard-x.y.z.jar server config.yml

#### Test

    curl http://127.0.0.1:8080/hello/json        