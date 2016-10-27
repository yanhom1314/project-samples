package com.example.jpa

import javax.persistence.{GeneratedValue, GenerationType, Id, MappedSuperclass}

@MappedSuperclass
trait Thing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = _
}
