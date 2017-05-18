package test

trait Ignore

case class A(a: Int)

case class C(a: A) extends Ignore

case class B(a: A, d: Int)