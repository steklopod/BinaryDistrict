package wtf.scala.e03

object Fibonacci {

  /**
    * Compute the n th Fibonacci number using recursion
    * https://en.wikipedia.org/wiki/Fibonacci_number
    *
    * We assume that fib(1) = 0, fib(2) = 1, and so on
    * @param n
    * @return
    */
  def fibRecursive(n: Int): Long = ???

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
  def fibMemo(n: Int): Long = ???

}
