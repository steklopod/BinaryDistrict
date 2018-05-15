package wtf.scala.e06

import org.scalatest.{FunSuite, Matchers}

class MapModificationsSpec extends FunSuite with Matchers {

  test("power") {
    MapModifications.power(Map(1 -> 1, 2 -> 1)) should contain only((1, 1) -> 1, (2, 1) -> 2)
    MapModifications.power(Map(2 -> 4, 5 -> 2, 3 -> 2)) should contain only((2, 4) -> 16, (5, 2) -> 25, (3, 2) -> 9)
  }

  test("revert") {
    val res1 = MapModifications.revert(Map(1 -> 3, 2 -> 4))
    res1 should have size(2)
    res1(3) should contain only(1)
    res1(4) should contain only(2)

    val res2 = MapModifications.revert(Map(1 -> 3, 2 -> 3, 4 -> 5))
    res2 should have size(2)
    res2(3) should contain only(1, 2)
    res2(5) should contain only(4)
  }

}
