package com.example.jpa

import javax.persistence.{GeneratedValue, GenerationType, Id, MappedSuperclass}

@MappedSuperclass
trait BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = _
}
