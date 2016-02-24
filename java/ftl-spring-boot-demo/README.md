## Spring Boot Integration FreeMarker Demo Project

## i18n
+ 使用`java -jar executable=x.y.z.jar`运行时需要制定MesaageSource中的baseName，否则无法获取`key`内容

        @Bean
        public MessageSource messageSource() {
            final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:/messages");
            messageSource.setFallbackToSystemLocale(false);
            messageSource.setCacheSeconds(0);
        
            return messageSource;
        }

## 开发模式
+ `java -Ddemo.load.dir=src\main\resources -jar x.y.z.jar`
+ `demo.load.dir`参数指定相对当前路径的静态资源根路径。

        