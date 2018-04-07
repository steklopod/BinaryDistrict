package wtf.scala.e03.lecture

import scala.annotation.tailrec

object TailRecursion extends App {

  def factorial(n: Int): Int = {
    if (n == 0) 1 else n * factorial(n - 1)
  }

  factorial(5) // 120


  def factorialTailRec(n: Long): Long = {
    @tailrec
    def factorialAccum(acc: Long, n: Long): Long = {
      if (n == 0) acc else factorialAccum(n * acc, n - 1)
    }
    factorialAccum(1, n)
  }
}