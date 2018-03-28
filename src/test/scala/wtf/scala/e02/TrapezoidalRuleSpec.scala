package wtf.scala.e02

import org.scalatest.{FunSuite, Matchers}

class TrapezoidalRuleSpec extends FunSuite with Matchers {

  def linear(x: Double) = x
  def square(x: Double) = x * x

  val epsilon = 0.0001

  test("TrapezoidalRule should return zero if leftX == rightX") {
    val linearResult = TrapezoidalRule.integrate(linear, 0, 0)
    linearResult should equal(0)

    val squareResult = TrapezoidalRule.integrate(square, 0, 0)
    squareResult should equal(0)
  }

  test("TrapezoidalRule should calculate integral from linear function") {
    val result = TrapezoidalRule.integrate(linear, 0, 1)
    result should equal(0.5 +- epsilon)
  }

  test("TrapezoidalRule should calculate integral from square function") {
    val result = TrapezoidalRule.integrate(square, 0, 1)
    result should equal(0.3333 +- epsilon)
  }

}
