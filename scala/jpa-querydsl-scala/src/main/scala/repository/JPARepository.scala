package repository

import javax.persistence.EntityManager

import com.google.inject.Provider
import com.querydsl.core.types.{EntityPath, Expression}
import com.querydsl.jpa.HQLTemplates
import com.querydsl.jpa.impl.{JPADeleteClause, JPAQuery}

import scala.reflect._

abstract class JPARepository[T: Manifest, ID] extends Repository[T, ID] {

  val em: Provider[EntityManager]

  val ct = classTag[T].runtimeClass

  protected def selectFrom(entity: EntityPath[T]): JPAQuery[T] = select(entity).from(entity)


  protected def select(select: Expression[T]): JPAQuery[T] = new JPAQuery(em.get(), HQLTemplates.DEFAULT).select(select)


  protected def delete(entity: EntityPath[_]): JPADeleteClause = new JPADeleteClause(em.get(), entity, HQLTemplates.DEFAULT)


  protected def detach(entity: T): Unit = em.get().detach(entity)

  override def findById(id: ID): T = em.get().find(ct, id).asInstanceOf[T]

  protected def find[E](t: Class[E], id: ID): E = em.get().find(t, id)

  protected def persist[E](entity: E): Unit = em.get().persist(entity)

  protected def merge[E](entity: E): E = em.get().merge(entity)

  protected def remove(entity: T) = em.get().remove(entity)
}