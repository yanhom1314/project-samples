#### JSF 2.1+
+ Maven:

		<dependencies>
		    <dependency>
		        <groupId>com.sun.faces</groupId>
		        <artifactId>jsf-api</artifactId>	
		        <version>X.Y.Z</version>	        
		    </dependency>
		    <dependency>
		        <groupId>com.sun.faces</groupId>
		        <artifactId>jsf-impl</artifactId>		        
		        <version>X.Y.Z</version>
		    </dependency>
		</dependencies>
		
##### Bean Inject
+ JSFA:

		@ManagedBean(name="jsfa")
		@SessionScoped
		public class JSFA{...}

+ JSFB:		

		@ManagedBean(name="jsfb")
		@RequestScoped
		public class JSFB{
			@ManagedProperty(value = "#{jsfa}")
			private JSFA jsfa;
			public void setJsfa(JSFA a){this.jsfa = a;}
			public JSFA getJsfa(){return this.jsfa;}
		}		

#### Spring Integration
+ Maven:
		
		<dependencies>
			<dependency>
		        <groupId>org.springframework</groupId>
			    <artifactId>spring-webmvc</artifactId>
			    <version>X.Y.Z</version>
		    </dependency>
		</dependencies>

+ Service.java:		

		@Service("bean")
		public class Service {}

+ JSFA.java:
		
		@Component
		@Scope("session")
		public class JSFA{...}

+ JSFB.java:
		
		@Component
		@Scope("request")
		public class JSFB{
			@Autowired
			private JSFA jsfa;
			public JSFA getJsfa(){return this.jsfa;}
		}			

* 当使用`@Component`与`@Scope()`代替`@ManagedBean`与`@SessionScoped @RequestScoped ...`时，`@ManagedProperty`不能工作，可以使用`@Value`代替。

* 获取`request param`可以使用`((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("param")`，但是要注意`@Scope`。

* 也可以在@ManageBean中直接获取Spring管理的Bean:`FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean([T.class]);`

+ faces-config.xml : 
	
		<?xml version="1.0" encoding="UTF-8"?>
		<faces-config xmlns="http://xmlns.jcp.org/xml/ns/javaee"
		              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		              xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://www.oracle.com/webfolder/technetwork/jsc/xml/ns/javaee/web-facesconfig_2_2.xsd" version="2.2">

		    <application>
		        <el-resolver>
		            org.springframework.web.jsf.el.SpringBeanFacesELResolver
		        </el-resolver>
		    </application>		
		</faces-config>

+ applictionContext.xml:

		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
		       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		       xmlns:context="http://www.springframework.org/schema/context"
		       xsi:schemaLocation="http://www.springframework.org/schema/beans
		        http://www.springframework.org/schema/beans/spring-beans.xsd
		        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
		
		    <context:component-scan base-package="x.y.z"/>
		</beans>

+ web.xml:

		<?xml version="1.0" encoding="UTF-8"?>
		<web-app id="demo-jsf-2" version="3.1"
		         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
		         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">
		    <display-name>demo-jsf-2</display-name>		
		    <context-param>
		        <param-name>javax.faces.CONFIG_FILES</param-name>
		        <param-value>
		            /WEB-INF/config/faces-config.xml
		        </param-value>
		    </context-param>		
		    <context-param>
		        <param-name>javax.faces.DEFAULT_SUFFIX</param-name>
		        <param-value>.xhtml</param-value>
		    </context-param>	
		    <context-param>
		        <param-name>javax.faces.PROJECT_STAGE</param-name>
		        <param-value>Development</param-value>
		    </context-param>
		    <context-param>
		        <param-name>contextConfigLocation</param-name>
		        <param-value>/WEB-INF/config/applicationContext*.xml</param-value>
		    </context-param>
		    <listener>
		        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
		    </listener>		
		    <listener>
		        <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
		    </listener>		
		    <servlet>
		        <servlet-name>Faces Servlet</servlet-name>
		        <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		        <load-on-startup>1</load-on-startup>
		    </servlet>		
		    <servlet-mapping>
		        <servlet-name>Faces Servlet</servlet-name>
		        <url-pattern>*.xhtml</url-pattern>
		    </servlet-mapping>
		</web-app>				

#### JavaEE XSD
+ (JavaEE)[http://www.oracle.com/webfolder/technetwork/jsc/xml/ns/javaee/index.html]
