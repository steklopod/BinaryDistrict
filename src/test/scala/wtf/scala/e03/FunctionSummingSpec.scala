package wtf.scala.e03

import org.scalatest.{FunSuite, Matchers}
import wtf.scala.util.MathFunctions

class FunctionSummingSpec extends FunSuite with Matchers with MathFunctions {

  test("FunctionSumming should sum linear function") {
    val res = FunctionSumming.sumFunction(n => linear(n))(0, 2)
    res should equal(3.0 +- 0.001)
  }

  test("FunctionSumming should sum quadratic function") {
    val res = FunctionSumming.sumFunction(n => square(n))(1, 3)
    res should equal(14.0 +- 0.001)
  }

}
