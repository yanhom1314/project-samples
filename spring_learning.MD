
##### IE中JSON格式下载的问题：
+ 配置文件中增加以下内容：
	
		<mvc:annotation-driven>
        	<mvc:message-converters>
            	<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                	<property name="supportedMediaTypes" value="text/plain;charset=UTF-8"/>
            	</bean>
        	</mvc:message-converters>
    	</mvc:annotation-driven>
