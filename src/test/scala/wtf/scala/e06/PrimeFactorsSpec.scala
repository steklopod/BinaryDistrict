package wtf.scala.e06

import org.scalatest.{FunSuite, Matchers}

class PrimeFactorsSpec extends FunSuite with Matchers {

  test("Should find prime factors of prime number") {
    PrimeFactors.primeFactors(7) should contain only(7)
  }

  test("Should find prime factors of non prime number") {
    PrimeFactors.primeFactors(25) should contain theSameElementsInOrderAs(List(5, 5))
    PrimeFactors.primeFactors(140) should contain theSameElementsInOrderAs(List(2, 2, 5, 7))
  }

  test("Should find all prime factors with multiplicity of prime number") {
    PrimeFactors.primeFactorsMultiplicity(7) should contain only(7 -> 1)
  }

  test("Should find all prime factors with multiplicity of non prime number") {
    PrimeFactors.primeFactorsMultiplicity(25) should contain only(5 -> 2)
    PrimeFactors.primeFactorsMultiplicity(140) should contain theSameElementsAs(Map(2 -> 2, 5 -> 1, 7 -> 1))
  }
}
