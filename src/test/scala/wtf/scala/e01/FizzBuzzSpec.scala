package wtf.scala.e01

import org.scalatest._

class FizzBuzzSpec extends FlatSpec with Matchers {
  import FizzBuzz._

  "FizzBuzz" should "return fizz if the number is dividable by 3" in {
    val fizzIndexes = (1 until 100).filter(i => i % 3 == 0 && i % 5 != 0).toSet
    val results = fizzBuzzUntil(100)
    val filteredByFizzIndexesValues = results.zipWithIndex.filter(p => fizzIndexes(p._2)).map(_._1)
    results should have size(100)
    filteredByFizzIndexesValues.foreach(_ should be ("fizz"))
  }

  it should "return buzz if the number is dividable by 5" in {
    val buzzIndexes = (1 until 100).filter(i => i % 3 != 0 && i % 5 == 0).toSet
    val results = fizzBuzzUntil(100)
    val filteredByBuzzIndexesValues = results.zipWithIndex.filter(p => buzzIndexes(p._2)).map(_._1)
    results should have size(100)
    filteredByBuzzIndexesValues.foreach(_ should be ("buzz"))
  }

  it should "return fizzbuzz if the number is dividable by 3 and 5" in {
    val fizzbuzzIndexes = (0 until 100).filter(_ % 15 == 0).toSet
    val results = fizzBuzzUntil(100)
    val filteredByFizzbuzzIndexesValues = results.zipWithIndex.filter(p => fizzbuzzIndexes(p._2)).map(_._1)
    results should have size(100)
    filteredByFizzbuzzIndexesValues.foreach(_ should be ("fizzbuzz"))
  }

  it should "return fizzbuzz if the number is zero" in {
    val results = fizzBuzzUntil(1)
    results.head should be ("fizzbuzz")
  }

  it should "throw the illegal argument exception if the number is less that one" in {
    assertThrows[IllegalArgumentException] {
      fizzBuzzUntil(0)
    }
  }

  it should "return the same number if no other requirement is fulfilled" in {
    val numberIndexes = (0 until 100).filter(i => !(i % 3 == 0 || i % 5 == 0)).toSet
    val results = fizzBuzzUntil(100)
    val filteredByNumberIndexesValues = results.zipWithIndex.filter(p => numberIndexes(p._2))
    results should have size(100)
    filteredByNumberIndexesValues.foreach {
      case (value, index) =>
        value should be (index.toString())
    }
  }
}
