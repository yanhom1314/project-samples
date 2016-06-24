package repository


import javax.inject.{Inject, Singleton}
import javax.persistence.EntityManager

import com.google.inject.Provider
import com.google.inject.persist.Transactional
import com.querydsl.core.types.Predicate
import jpa.entity.Person
import jpa.entity.QPerson._

@Transactional
@Singleton
class PersonRepository @Inject()(val em: Provider[EntityManager]) extends JPARepository[Person, java.lang.Long] {
  def findAll(expr: Predicate) = selectFrom(person).where(expr).fetch()

  def all = selectFrom(person).fetch()
}
