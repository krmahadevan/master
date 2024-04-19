# Sample upstream app

## Pre-requisites

* JDK17
* Maven
* Upstream app which is represented by [worker](https://github.com/krmahadevan/worker)

## Running this application

```shell
mvn clean test-compile spring-boot:run
```


## Running this application with a Proxy server

```shell
mvn clean test-compile spring-boot:run -Dspring-boot.run.jvmArguments="-Dhttp.proxyHost=localhost -Dhttp.proxyPort=8081"
```