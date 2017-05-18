package test

trait Ignore

case class A(a: Int)

case class B(a: A with Ignore, d: Int)

//add type A with Ignore
class C(c: Int) extends A(c) with Ignore
