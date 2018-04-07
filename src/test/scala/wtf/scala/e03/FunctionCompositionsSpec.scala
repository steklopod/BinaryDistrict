package wtf.scala.e03

import org.scalatest.{FunSuite, Matchers}
import wtf.scala.util.MathFunctions

class FunctionCompositionsSpec extends FunSuite with Matchers with MathFunctions {

  test("compose should work correct 1") {
    val composed = FunctionCompositions.compose(square, cube)

    composed(1) should equal(1)
    composed(2) should equal(64)
  }

  test("compose should work correct 2") {
    val foo = (x: String) => "foo " + x
    val bar = (x: String) => "bar " + x
    val composed = FunctionCompositions.compose(foo, bar)
    composed("abc") should equal("foo bar abc")
  }

  test("curry should work correct") {
    val multUncurried = (a: Int, b: Int) => a * b
    val multCurried = FunctionCompositions.curry(multUncurried)

    multCurried(2)(4) should equal(multUncurried(2, 4))
    multCurried(3)(7) should equal(multUncurried(3, 7))
  }

  test("uncurry should work correct") {
    val sumCurried = (a: Int) => (b: Int) => a + b
    val sumUncurried = FunctionCompositions.uncurry(sumCurried)

    sumUncurried(-2, 1) should equal(sumCurried(-2)(1))
    sumUncurried(4, 7) should equal(sumCurried(4)(7))
  }

  test("modulus should work correct with linear") {
    val linearModulus = FunctionCompositions.modulus(linear)
    linearModulus(2) should equal(2)
    linearModulus(-2) should equal(2)
    linearModulus(0) should equal(0)
  }

  test("modulus should work correct with cube") {
    val linearModulus = FunctionCompositions.modulus(cube)
    linearModulus(2) should equal(8)
    linearModulus(-2) should equal(8)
    linearModulus(0) should equal(0)
  }

}
