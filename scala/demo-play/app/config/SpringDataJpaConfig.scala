package config

import javax.persistence.{EntityManagerFactory, Persistence}

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.HibernateExceptionTranslator
import org.springframework.orm.jpa.JpaTransactionManager

@Configuration
@EnableJpaRepositories(basePackages = Array("entities"))
@ComponentScan(Array("entities"))
class SpringDataJpaConfig {
  @Bean def entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("default")

  @Bean def hibernateExceptionTranslator(): HibernateExceptionTranslator = new HibernateExceptionTranslator()

  @Bean def transactionManager(): JpaTransactionManager = new JpaTransactionManager()

}
