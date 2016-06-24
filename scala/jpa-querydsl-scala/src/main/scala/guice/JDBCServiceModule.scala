package guice

import java.util.Properties
import javax.inject.{Named, Singleton}
import javax.sql.DataSource

import com.google.inject.matcher.Matchers
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Provides}
import com.querydsl.sql.{Configuration, H2Templates}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import scala.collection.JavaConversions._

class JDBCServiceModule extends AbstractModule {
  override def configure(): Unit = {

    val properties = new Properties()

    val input = this.getClass.getClassLoader.getResourceAsStream("jdbc.properties")
    try {
      properties.load(input)
      properties.entrySet().map(t => t.getKey.asInstanceOf[String] -> t.getValue.asInstanceOf[String])
        .foreach(t => bind(classOf[String]).annotatedWith(Names.named(t._1)).toInstance(t._2))

      bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[Transactional]), new TransactionInterceptor(getProvider(classOf[ConnectionContext])))
    }
    catch {
      case e: Exception => e.printStackTrace()
    }
    finally {
      input.close()
    }
  }

  @Provides
  @Singleton
  def configuration(): Configuration = new Configuration(new H2Templates())

  @Provides
  @Singleton
  def dataSource(@Named("jdbc.user") user: String,
                 @Named("jdbc.password") password: String,
                 @Named("jdbc.url") url: String,
                 @Named("jdbc.driver") driver: String,
                 @Named("dataSourceClassName") dataSourceClassName: String): DataSource = {

    println(s"${user} ${password} ${url} ${driver}")
    Class.forName(driver)
    val hf = new HikariConfig()
    hf.setDataSourceClassName(dataSourceClassName)
    hf.addDataSourceProperty("user", user)
    hf.addDataSourceProperty("password", password)
    hf.addDataSourceProperty("URL", url)

    val hd = new HikariDataSource(hf)
    println(s"hd:${hd}")
    hd
  }
}
