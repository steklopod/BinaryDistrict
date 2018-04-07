package wtf.scala.e03

import org.scalatest.{FunSuite, Matchers}

class OddEvenSpec extends FunSuite with Matchers {

  test("OddEven should process odd number correct") {
    OddEven.process(2) should equal("Positive")
  }

  test("OddEven should process even number correct") {
    OddEven.process(3) should equal("Negative")
  }

  test("OddEven should throw exception if after input number modification you receive zero") {
    intercept[MatchError] {
      OddEven.process(0)
    }
  }

}
