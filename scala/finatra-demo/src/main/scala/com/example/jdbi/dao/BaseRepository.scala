package com.example.jdbi.dao

import java.util

trait BaseRepository[T] {
  def save(d: T)

  def delete(d: T)

  def update(d: T)

  def findAll(): util.List[T]

  def findById(id: Long): T

  def count(): Long
}
