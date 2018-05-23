package wtf.scala.e03

import scala.collection.mutable

object Fibonacci {

  /**
    * Compute the n th Fibonacci number using recursion
    * https://en.wikipedia.org/wiki/Fibonacci_number
    *
    * We assume that fib(1) = 0, fib(2) = 1, and so on
    * @param n
    * @return
    */
  def fibRecursive(n: Int): Long = n match {
    case n if n == 1 => 0
    case n if n == 2 => 1
    case n => fibRecursive(n - 1) + fibRecursive(n - 2)
  }

  /**
    * Compute the n th Fibonacci number using memoization
    * https://en.wikipedia.org/wiki/Fibonacci_number
    *
    * We assume that fib(1) = 0, fib(2) = 1, and so on
    *
    * Hint: to store computed Fibonacci numbers you can use scala.collection.mutable.HashMap[Int, Long]
    * @param n
    * @return
    */
  private val storage = mutable.HashMap.empty[Int, Long]
  def fibMemo(n: Int): Long = {
    storage.getOrElseUpdate(n, fibRecursive(n))
  }

}
