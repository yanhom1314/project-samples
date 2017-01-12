##JPA

####JPA关系注解
Entiyty关系维护中常见使用的@OneToOne、@ManyToOne、@OneToMany、@ManyToMany。
在这些注解中，属性：
**cacade**：在那个Class中定义，表示操作自身时对映射的Class产生级联操作。`CascadeType.ALL`、`CascadeType.MERGE`等，设置级联操作，当操作当前实体时级联操作目标实体；
**mappedBy**：映射关系维护方，如果不设置会生成中间表，如果设置则在被维护方生称外键；并可以在被维护端使用`@JoinColumn("column_name")`设置外键字段名。
例如：
A.java

		@OneToOne(mappedBy="a")B b;

B.java

		@OneToOne()
		@JoinColumn(name="a_id")A a;

则在B实体对应的表中会生成A实体的外键(a_id)。
**fetch**:`FetchType.LAZY`延迟加载，`FetchType.EGAR`立即加载

##@OneToOne
+ IdCard.java
		
		public class IdCard{
			@OneToOne(optional = true, cascade = CascadeType.REMOVE, mappedBy = "idCard")
			private Person person;
		}
+ Person.java

		public class Person{
			@OneToOne(optional = false, cascade = Array(CascadeType.REMOVE))
			@JoinColum(name="p_id")
			private IdCard idCard;
		}

##@ManyToOne
One：
Person.java
@OneToMany(mappedBy = "person", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
private Set<IdCard> idCards;

Many：
IdCard.java
@ManyToOne(optional = false, cascade = Array(CascadeType.REMOVE))
private Person person;

@ManyToMany
A.java：
@ManyToMany(cascade={CascadeType.PERSIST})     
@JoinTable(name="A_B",joinColumns={@JoinColumn(name="a_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="b_id",referencedColumnName="id")})   
private List<B> bs;

B.java：
@ManyToMany(mappedBy = "bb") 
private List<A> as;

optional：为true时则映射关联对象可为null，为false时则不能使用null对象；
mappedBy：mappedBy属性避免生成中间表；
cascade：在对声明此属性的类操作时，同时对有映射关系的类进行操作；

在@OneToOne、@ManyToOne中可以使用@JoinColumn注解
@JoinColumn(name = "user_id", nullable = true, unique = false)
在@ManyToMany中需要使用@JoinTable定义中间表
@JoinTable(name="A_B",joinColumns={@JoinColumn(name="a_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="b_id",referencedColumnName="id")})

JPA实现
Hibernate(推荐)
http://hibernate.org
OpenJPA
http://openjpa.apache.org/

*测试过程中发现OpenJPA的性能不如Hibernate差距比较明显。
Database连接池
BoneCP(推荐)
http://jolbox.com/
<property name="hibernate.service.jdbc.connections.spi.provider_class" value="com.jolbox.bonecp.provider.BoneCPConnectionProvider"/>
<!-- BoneCP settings -->
<property name="bonecp.partitionCount" value="3"/>
<property name="bonecp.maxConnectionsPerPartition" value="20"/>
<property name="bonecp.minConnectionsPerPartition" value="10"/>
<property name="bonecp.acquireIncrement" value="5"/>
<property name="bonecp.poolAvailabilityThreshold" value="20"/>
<property name="bonecp.releaseHelperThreads" value="3"/>
<property name="bonecp.idleMaxAge" value="240"/>
<property name="bonecp.idleConnectionTestPeriod" value="60"/>
<property name="bonecp.statementsCacheSize" value="20"/>

C3P0 
http://sourceforge.net/projects/c3p0/
<property name="hibernate.connection.provider_class" value="org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider"/>
<!-- 最小连接数 -->
<property name="hibernate.c3p0.min_size" value="5"/>
<!-- 最大连接数 -->
<property name="hibernate.c3p0.max_size" value="20"/>
<!--最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 -->
<property name="c3p0.maxIdleTime" value="60"/>
<!-- 获得连接的超时时间,如果超过这个时间,会抛出异常，单位毫秒 -->
<property name="hibernate.c3p0.timeout" value="3000"/>
<!-- 最大的PreparedStatement的数量 -->
<property name="hibernate.c3p0.max_statements" value="3000"/>
<!-- 每隔120秒检查连接池里的空闲连接 ，单位是秒-->
<property name="hibernate.c3p0.idle_test_period" value="120"/>

Hibernate属性

完整的persistence.xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
    <persistence-unit name="default" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <class>models.Teacher</class>
        <class>models.Student</class>
        <class>models.Book</class>
        <properties>
            <!-- provider -->
            <property name="hibernate.service.jdbc.connections.spi.provider_class"
                      value="com.jolbox.bonecp.provider.BoneCPConnectionProvider"/>
            <!-- Postgresql -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgresPlusDialect"/>
            <property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
            <property name="hibernate.connection.url" value="jdbc:postgresql://127.0.0.1:5432/test"/>
            <property name="hibernate.connection.username" value="postgres"/>

            <property name="hibernate.max_fetch_depth" value="3"/>
            <property name="hibernate.jdbc.fetch_size" value="100"/>
            <property name="hibernate.jdbc.batch_size" value="50"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>

            <property name="hibernate.show_sql" value="false"/>
            <property name="hibernate.format_sql" value="false"/>
            <!-- BoneCP settings -->
            <property name="bonecp.partitionCount" value="3"/>
            <property name="bonecp.maxConnectionsPerPartition" value="20"/>
            <property name="bonecp.minConnectionsPerPartition" value="10"/>
            <property name="bonecp.acquireIncrement" value="5"/>
            <property name="bonecp.poolAvailabilityThreshold" value="20"/>
            <property name="bonecp.releaseHelperThreads" value="3"/>
            <property name="bonecp.idleMaxAge" value="240"/>
            <property name="bonecp.idleConnectionTestPeriod" value="60"/>
            <property name="bonecp.statementsCacheSize" value="20"/>
        </properties>
    </persistence-unit>
</persistence>

*测试过程中BoneCP的新能要提高20-30%左右。

JPA生成DDL

val e3config = new Ejb3Configuration()
e3config.addAnnotatedClass(classOf[models.Teacher])
val config = e3config.getHibernateConfiguration
val dp = new Properties()
dp.put("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect")
config.generateSchemaCreationScript(Dialect.getDialect(dp)).toList.foreach {
  line =>
     println(line+";\n")
}

Ejb3Configuration已经Deprecated，在5.0版本中可能会用EntityManagerBuilderFactory代替。

JPA延迟加载
会话管理
	无论是使用JPA或者Hibernate在延迟加载处理中，我们关心的问题无非就是，Session或者EntityManager什么时候打开，什么时候关闭！而解决延迟加载的问题核心在于解决Session或者EntityManager的开关时效管理。
J2EE WEB应用
基于Filter接口规范来处理，配合Spring使用，在Spring ORM的支持包中已经有解决方案：OpenEntityManagerInViewFilter在web.xml配置即可：
<filter>
<filter-name>hibernateFilter</filter-name>
<filter-class>
org.springframework.orm.jpa.support. OpenEntityManagerInViewFilter
</filter-class>
<!-- 与applicationContext-*.xml中EntityManagerFactory的bean id一致。-->
<init-param>
           <param-name>entityManagerFactory</param-name>
           <param-value>entityManagerFactory</param-value>
 </init-param>
</filter>
<filter-mapping>
<filter-name>hibernateFilter</filter-name>
<url-pattern>*.action</url-pattern>
</filter-mapping>
Hiberate在 Spring中的解决方案与JPA是非常类似的。
OpenSessionInViewFilter在web.xml中：
<filter>
<filter-name>hibernateFilter</filter-name>
<filter-class>
org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
</filter-class>
<!-- 是否单个Session-->
<init-param>
<param-name>singleSession</param-name>
    <param-value>false</param-value>
</init-param>
<!-- 你在applicationCointext*.xml中配置的SessionFactory bean的id名-->
<init-param>
    <param-name>sessionFactoryBeanName</param-name>
     <param-value>mySessionFactory</param-value>
</init-param>
</filter>
<filter-mapping>
<filter-name>hibernateFilter</filter-name>
<url-pattern>*.action</url-pattern>
</filter-mapping>

注意：
	OpenSessionInViewFilter处理的是Spring通过在ServletContext 中载入配置，通过WebApplicationContextUtils.getWebApplicationContext(ServletContext sc)方法获得ApplicationContext，如果想要自行载入构建的ApplicationContext被OpenSessionInViewFilter处理，需要修改其源代码。

OpenSessionInViewInterceptor
这个Interceptor使用在Spring MVC的结构，使用Spring MVC的可以在applicationContext-*.xml中定义注入即可，它的ref指向SessionrFactory ，它也是SimpleUrlHandlerMapping的interceptors属性的list之一

服务层
	动态代理（JPA）
	使用动态代理AOP结构，来管理EntityManager，这种方案不依赖于容器，在后台服务、测试中都可以良好使用。先看代码：
OpenEntityManagerHandler.java

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.neto.util.JSFUtils;

public class OpenEntityManagerHandler implements InvocationHandler {
	private static final Logger logger = Logger
			.getLogger(OpenEntityManagerHandler.class);
	private ApplicationContext ctx = null;
	private EntityManagerFactory emFactory = null;	
	private Object obj;

	public OpenEntityManagerHandler() {
		if (ctx == null)
			ctx = getApplicationContext();
		if (emFactory == null)
			emFactory = (EntityManagerFactory) ctx.getBean(
					"entityManagerFactory", EntityManagerFactory.class);
	}

	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Object result = null;
		boolean isOpen = false;
		if (TransactionSynchronizationManager.hasResource(emFactory)) {
			isOpen = true;
		} else {
			// 开
			logger.info("[open entity manager!]");
			EntityManager em = emFactory.createEntityManager();
			TransactionSynchronizationManager.bindResource(emFactory,
					new EntityManagerHolder(em));
		}
		try {
			result = arg1.invoke(this.obj, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关
				if (!isOpen) {
					EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
							.unbindResource(emFactory);
					emHolder.getEntityManager().close();
					logger.info("[close entity manager!]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	public Object bind(Object obj) {
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), this);
	}
private ApplicationContext getApplicationContext(){
	//TODO
	/*
	*自行获得ApplicationContext
	*/
}
}


	演示
		接口
		ITask.java
public interface ITask {
			public void process();
}

实现
ITaskImpl.java

public class ItaskImpl implements Itask{
public void process(){
	System.out.println(“Hello World!”);
}
}

使用
	  ……
OpenEntityManagerHandler handler = new OpenEntityManagerHandler();
	  ITask task = (ITask) handler.bind(new ITaskImpl());
	  task.process();
      ……

		运行结果
		[open entity manager!]
		Hello World!
		[close entity manager!]

	是不是很简单！
	深入
		以上的方案不修改的Service/DAO模型，侵入性小，测试方便，但是有一些局限性。更良好的方案可以将OpenEntityManagerHandler进行一些修改，
1.	加入private String FACTORY_NAME=DEFAULT_ENTITYMANGER_FACTORY，修改获取EntityManagerFactory的方法。
2.	在applicationContext-*.xml中配置bean，并ref指向EntityManagerFactory的bean。
3.	修改你的Service/DAO模型，引入OpenEntityManagerHandler。
留给有兴趣的自己研究，修改一下。
补充
	Hiberate的解决方案和JPA非常类似，不再重复了。
	
范例运行
	范例中是一个简单的动态代理运行的例子，其中把载入Spring和JPA、Hiberante的依赖去掉了，让大家可以看一下结构，真正配合Spring、JPA/Hiberante使用，需要大家自己增添代码，感兴趣的自己丰富一下代码，希望对你有所启发。

总结
	文中提到了四种解决方案，
第一、	使用OpenEntityManagerInViewFilter/OpenSessionInViewFilter，在Spring MVC结构中还可以更方便简洁的使用 OpenEntityManagerInViewInterceptor/OpenSessionInViewInterceptor。
第二、	使用非注入的AOP的结构，应用动态代理，不依赖于任何容器。
第三、	配合Spring使用，需要修改Service/DAO模型，引入自实现的OpenEntityManagerInterceptor/OpenSessionInterceptor


#### 经典教程
[ObjectDB](http://www.objectdb.com/java/jpa)