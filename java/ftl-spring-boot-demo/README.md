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
* 打开一个终端: `gradle -Dlocal.doc.root=src\main\resources bootRun`
* 打开另一个终端： `gradle build --continuous`
__`local.doc.root`参数指定资源根路径__

## 运行模式
+ `gradle clean build -x test`
+ `java -jar x.y.z.jar`

        