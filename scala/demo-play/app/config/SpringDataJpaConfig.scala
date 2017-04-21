package config

import javax.persistence.{EntityManagerFactory, Persistence}

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager

@Configuration
@EnableJpaRepositories(basePackages = Array("entities"))
@ComponentScan(Array("entities.repo"))
class SpringDataJpaConfig {
  @Bean def entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("default")

  @Bean def transactionManager(): JpaTransactionManager = new JpaTransactionManager()
}
