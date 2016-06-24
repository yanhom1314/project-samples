package repository

import javax.persistence.EntityManager

import com.google.inject.Provider
import com.querydsl.core.types.{EntityPath, Expression}
import com.querydsl.jpa.HQLTemplates
import com.querydsl.jpa.impl.{JPADeleteClause, JPAQuery}

import scala.reflect._

abstract class JPARepository[T, ID](implicit ct: ClassTag[T]) {

  protected val em: Provider[EntityManager]

  protected val c = ct.runtimeClass

  protected def selectFrom(entity: EntityPath[T]): JPAQuery[T] = select(entity).from(entity)

  protected def select(select: Expression[T]): JPAQuery[T] = new JPAQuery(em.get(), HQLTemplates.DEFAULT).select(select)

  protected def delete(entity: EntityPath[_]): JPADeleteClause = new JPADeleteClause(em.get(), entity, HQLTemplates.DEFAULT)

  def detach(entity: T): Unit = em.get().detach(entity)

  def findById(id: ID): T = em.get().find(c, id).asInstanceOf[T]

  def find[E](t: Class[E], id: ID): E = em.get().find(t, id)

  def persist[E](entity: E): Unit = em.get().persist(entity)

  def merge[E](entity: E): E = em.get().merge(entity)

  def remove(entity: T) = em.get().remove(entity)
}