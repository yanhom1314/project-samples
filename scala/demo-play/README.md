# Getting started with Play (Scala)

This project demonstrate how to create a simple CRUD application with Play integration Spring Data JPA.


## conf/application.conf

        play.modules.enabled += "modules.ModuleJava"  #modules.ModuleScala


*play.http.session.maxAge:不能过小，否则会频繁清除session，则CSRF测试就不正常*        

## Build & Run

        webpack
        sbt run

## Frontend
                           
        //全局安装webpack
        npm install vue-cli -g
        
        //初始化package.json
        cd frontend     //vue init webpack-simple frontend
        npm install
        
        //安装vue的依赖
        npm install vue vee-validate --save
        
        //安装babel的ES6 Loader 的开发依赖
        npm install babel-core babel-loader babel-preset-latest cross-env css-loader file-loader vue-loader vue-template-compiler webpack webpack-dev-server --save-dev
                       
#### vue vee-validate
+ [vue](https://github.com/vuejs/vue)
+ [vue-loader](https://github.com/vuejs/vue-loader)
+ [vee-validate](https://github.com/logaretm/vee-validate)

#### 前端
+ Ajax使用的是jQuery.ajax，也可以使用axios或者fetch

#### CSRF
+ `Twirl Template`: `<meta name="Csrf-Token" content="@helper.CSRF.getToken.value">`

+ `JavaScript`:
    
        var csrfToken = $('meta[name="Csrf-Token"]').attr("content");
        $("#list2").jqGrid({
                ...
                loadBeforeSend: function(jqXHR) {
                    // you should modify the next line to get the CSRF tocken
                    jqXHR.setRequestHeader('Csrf-Token', csrfToken);
                },
                ...
            });               