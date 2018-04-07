package wtf.scala.e01

import org.scalatest.{FunSuite, Matchers}

class IntegerRootSpec extends FunSuite with Matchers {

  test("IntegerRoot should throw IllegalArgumentException for negative integer") {
    assertThrows[IllegalArgumentException] {
      IntegerRoot.calculateRoot(-1)
    }
  }

  test("IntegerRoot should return -1 for non square integer") {
    val result1 = IntegerRoot.calculateRoot(7)
    result1 should equal(-1)

    val result2 = IntegerRoot.calculateRoot(111)
    result2 should equal(-1)
  }

  test("IntegerRoot should calculate integer root") {
    val result1 = IntegerRoot.calculateRoot(4)
    result1 should equal(2)

    val result2 = IntegerRoot.calculateRoot(100)
    result2 should equal(10)
  }

}