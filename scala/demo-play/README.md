# Getting started with Play (Scala)

This project demonstrate how to create a simple CRUD application with Play integration Spring Data JPA.


## conf/application.conf

        play.modules.enabled += "modules.ModuleJava"  #modules.ModuleScala

## Build & Run

        webpack
        sbt run

## Frontend
                           
        //全局安装webpack
        npm install vue-cli webpack -g
        
        //初始化package.json
        cd frontend     //vue init webpack-simple hello
        npm install
        
        //安装vue的依赖
        npm install vue vee-validate --save
        
        //安装babel的ES6 Loader 的开发依赖
        npm install babel-core babel-loader babel-preset-es2015 css-loader file-loader vue-loader vue-template-compiler webpack-dev-server --save-dev
                
        //安装html loacer 的开发依赖
        npm install html-loader --save-dev

#### vue vee-validate
+ [vue](https://github.com/vuejs/vue)
+ [vue-loader](https://github.com/vuejs/vue-loader)
+ [vee-validate](https://github.com/logaretm/vee-validate)


#### 前端
+ Ajax使用的是jQuery.ajax，也可以使用axios或者fetch

        
        