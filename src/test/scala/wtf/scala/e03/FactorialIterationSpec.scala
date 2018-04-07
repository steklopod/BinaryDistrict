package wtf.scala.e03

import org.scalatest.{FunSuite, Matchers}

class FactorialIterationSpec extends FunSuite with Matchers {

  test("FactorialIteration should compute factorial of 0") {
    FactorialIteration.factorial(0) should equal(1)
  }

  test("FactorialIteration should compute factorial of 1") {
    FactorialIteration.factorial(1) should equal(1)
  }

  test("FactorialIteration should compute factorial of 10") {
    FactorialIteration.factorial(10) should equal(3628800)
  }

}
