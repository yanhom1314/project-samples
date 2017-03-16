package com.example.jpa

import javax.persistence.{GeneratedValue, GenerationType, Id, MappedSuperclass}

import scala.beans.BeanProperty

@MappedSuperclass
trait BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Long = _
}
