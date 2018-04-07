package wtf.scala.e03

import org.scalatest.{FunSuite, Matchers}

class FibonacciSpec extends FunSuite with Matchers {

  test("The 1st Fibonacci number should be computed correct using recursion") {
    Fibonacci.fibRecursive(1) should equal(0)
  }

  test("The 3rd Fibonacci number should be computed correct using recursion") {
    Fibonacci.fibRecursive(3) should equal(1)
  }

  test("The 10th Fibonacci number should be computed correct using recursion") {
    Fibonacci.fibRecursive(10) should equal(34)
  }
  

  test("The 1st Fibonacci number should be computed correct using memoization") {
    Fibonacci.fibMemo(1) should equal(0)
  }

  test("The 3rd Fibonacci number should be computed correct using memoization") {
    Fibonacci.fibMemo(3) should equal(1)
  }

  test("The 10th Fibonacci number should be computed correct using memoization") {
    Fibonacci.fibMemo(10) should equal(34)
  }

}
