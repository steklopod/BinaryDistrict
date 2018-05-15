package wtf.scala.e06

import org.scalatest.{FunSuite, Matchers}

class RunLengthEncodingSpec extends FunSuite with Matchers {

  test("Pack duplicates") {
    val res1 = RunLengthEncoding.pack(List(1, 2, 3, 4))
    res1 should have size(4)
    res1(0) should contain only(1)
    res1(1) should contain only(2)
    res1(2) should contain only(3)
    res1(3) should contain only(4)

    val res2 = RunLengthEncoding.pack(List(1, 1, 1, 2, 3, 3, 4, 5, 5))
    res2 should have size(5)
    res2(0) should contain theSameElementsInOrderAs(List(1, 1, 1))
    res2(1) should contain only(2)
    res2(2) should contain theSameElementsInOrderAs(List(3, 3))
    res2(3) should contain only(4)
    res2(4) should contain theSameElementsInOrderAs(List(5, 5))

    val res3 = RunLengthEncoding.pack(List(1, 2, 2, 1, 1, 1))
    res3 should have size(3)
    res3(0) should contain only(1)
    res3(1) should contain theSameElementsInOrderAs(List(2, 2))
    res3(2) should contain theSameElementsInOrderAs(List(1, 1, 1))
  }

  test("Run length encode") {
    RunLengthEncoding.runLengthEncode(List(1, 2, 3, 4)).toList should contain theSameElementsInOrderAs(List((1, 1), (2, 1), (3, 1), (4, 1)))
    RunLengthEncoding.runLengthEncode(List(1, 1, 1, 2, 3, 3, 4, 5, 5)) should contain theSameElementsInOrderAs(List((1, 3), (2, 1), (3, 2), (4, 1), (5, 2)))
    RunLengthEncoding.runLengthEncode(List(1, 2, 2, 1, 1, 1)) should contain theSameElementsInOrderAs(List((1, 1), (2, 2), (1, 3)))
  }

  test("Run length decode") {
    RunLengthEncoding.runLengthDecode(List((1, 1), (2, 1), (3, 1), (4, 1))) should contain theSameElementsInOrderAs(List(1, 2, 3, 4))
    RunLengthEncoding.runLengthDecode(List((1, 3), (2, 1), (3, 2), (4, 1), (5, 2))) should contain theSameElementsInOrderAs(List(1, 1, 1, 2, 3, 3, 4, 5, 5))
    RunLengthEncoding.runLengthDecode(List((1, 1), (2, 2), (1, 3))) should contain theSameElementsInOrderAs(List(1, 2, 2, 1, 1, 1))
  }

  test("Endocode and decode together") {
    val numbers = List(1, 1, 3, 2, 5, 5, 5, 6, 5)
    RunLengthEncoding.runLengthDecode(RunLengthEncoding.runLengthEncode(numbers)) should contain theSameElementsInOrderAs(numbers)
  }
}
