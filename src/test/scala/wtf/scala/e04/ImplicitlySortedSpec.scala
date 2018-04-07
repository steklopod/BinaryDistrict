package wtf.scala.e04

import org.scalatest.{FunSuite, Matchers}

class ImplicitlySortedSpec extends FunSuite with Matchers {
  test("SortedList creation should fails when parameter is not sorted") {
    assertThrows[IllegalArgumentException](SortedList(List(5, 3, 4)))
  }

  test("SortedList creation should success when parameter list is sorted") {
    SortedList(List(3, 4, 5))
  }
  test("SortedList.list2sorted should sort collection") {
    SortedList.list2sorted(List(5, 4, 3)) should be(SortedList(List(3, 4, 5)))
  }
}
