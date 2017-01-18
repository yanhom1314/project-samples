# Getting started with Play (Scala)

This project demonstrate how to create a simple CRUD application with Play integration Spring Data JPA.


## conf/application.conf

        play.modules.enabled += "modules.ModuleJava"  #modules.ModuleScala

## Build & Run

        webpack
        sbt run

## Frontend
           
        //全局安装webpack
        npm install webpack -g
        npm install vue whatwg-fetch --save        
       
        //初始化package.json
        npm init
        
        //安装vue的依赖
        npm install vue vee-validate --save
        
        //安装babel的ES6 Loader 的开发依赖
        npm install babel babel-core babel-loader babel-preset-es2015 vue-loader vue-template-compiler --save-dev
        
        //安装html loacer 的开发依赖
        npm install html-loader --save-dev
        