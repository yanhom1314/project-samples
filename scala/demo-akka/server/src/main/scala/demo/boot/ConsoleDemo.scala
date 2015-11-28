package demo.boot

import scala.annotation.tailrec

object ConsoleDemo extends App {
  time(println(factorial(1500)))
  time(println(fac(1500, 1)))
  time(println(fibonacci(5)))
  time(println(fibonaccii(5, 0, 1)))
  time(println(fibonacci(11)))
  time(println(fibonaccii(11, 0, 1)))
  time(println(countChange(6, List(1, 2, 3, 4, 5))))
  time(println(countChangeii(6, List(1, 2, 3, 4, 5), 0, 1)))

  def factorial(n: BigInt): BigInt = {
    if (n == 0) 1 else n * factorial(n - 1)
  }

  @tailrec
  def fac(n: BigInt, acc: BigInt): BigInt = {
    if (n == 0) acc else fac(n - 1, n * acc)
  }

  def time(call: => Unit): Unit = {
    val start = System.currentTimeMillis()
    call
    val end = System.currentTimeMillis()
    println(s"time use ${end - start}ms.")
  }

  def fibonacci(n: BigInt): BigInt = {
    if (n <= 2) 1 else fibonacci(n - 1) + fibonacci(n - 2)
  }

  @tailrec
  def fibonaccii(n: BigInt, acc1: BigInt, acc2: BigInt): BigInt = {
    if (n < 2) acc2
    else {
      fibonaccii(n - 1, acc2, acc1 + acc2)
    }
  }

  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (coins.size == 0 || money < 0)
      0
    else
      countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }

  @tailrec
  def countChangeii(money: Int, coins: List[Int], acc1: Int, acc2: Int): Int = {
    if (money == 0) 1
    else if (coins.size == 0 || money < 0) 0
    else countChangeii(money - coins.head, coins.tail, acc2, acc1 + acc2)
  }
}
