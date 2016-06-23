package repository


import javax.inject.Inject
import javax.persistence.EntityManager

import com.google.inject.Provider
import com.google.inject.persist.Transactional
import com.querydsl.core.types.Predicate
import jpa.entity.Person
import jpa.entity.QPerson._

@Transactional
class PersonRepository @Inject()(val em: Provider[EntityManager]) extends JPARepository[Person, java.lang.Long] {

  override def findById(id: java.lang.Long): Person = find(classOf[Person], id)

  def findAll(expr: Predicate) = selectFrom(person).where(expr).fetch()

  def all = selectFrom(person).fetch()

}
