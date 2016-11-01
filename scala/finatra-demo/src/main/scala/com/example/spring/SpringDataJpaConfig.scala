package com.example.spring

import javax.persistence.{EntityManagerFactory, Persistence}

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.HibernateExceptionTranslator
import org.springframework.orm.jpa.JpaTransactionManager

@Configuration
@EnableJpaRepositories(basePackages = Array("com.example.jpa.repo"))
@ComponentScan(Array("com.example.jpa.repo"))
class SpringDataJpaConfig {
  @Bean def entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("postgres") //or h2

  @Bean def hibernateExceptionTranslator(): HibernateExceptionTranslator = new HibernateExceptionTranslator()

  @Bean def transactionManager(): JpaTransactionManager = new JpaTransactionManager()

}