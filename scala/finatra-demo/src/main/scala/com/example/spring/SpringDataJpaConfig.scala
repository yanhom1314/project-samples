package com.example.spring

import javax.persistence.{EntityManagerFactory, Persistence}

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.HibernateExceptionTranslator
import org.springframework.orm.jpa.JpaTransactionManager

@Configuration
@EnableJpaRepositories(basePackages = Array("com.example.jpa.repo"))
@ComponentScan(Array("com.example.jpa"))
class SpringDataJpaConfig {
  @Bean def entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("h2") // h2 or postgres

  @Bean def hibernateExceptionTranslator(): HibernateExceptionTranslator = new HibernateExceptionTranslator()

  @Bean def transactionManager(): JpaTransactionManager = new JpaTransactionManager()
}