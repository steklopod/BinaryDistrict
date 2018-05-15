package wtf.scala.e06

import org.scalatest.{FunSuite, Matchers}

class SeqModificationsSpec extends FunSuite with Matchers {

  test("duplicateN") {
    SeqModifications.duplicateN(2, Vector(1, 2, 3)) should contain theSameElementsInOrderAs(Vector(1, 1, 2, 2, 3, 3))
    SeqModifications.duplicateN(3, Vector(5, 5)) should contain theSameElementsInOrderAs(Vector(5, 5, 5, 5, 5, 5))
  }

  test("removeN") {
    SeqModifications.removeN(4, Vector(1, 2)) should contain theSameElementsInOrderAs(Vector(1, 2))
    SeqModifications.removeN(3, Vector(1, 2, 3, 4, 5, 6, 7)) should contain theSameElementsInOrderAs(Vector(1, 2, 4, 5, 7))
  }

  test("rotate") {
    SeqModifications.rotate(1, List(1, 2, 3, 4)) should contain theSameElementsInOrderAs(List(2, 3, 4, 1))
    SeqModifications.rotate(-2, List(1, 2, 3, 4, 5)) should contain theSameElementsInOrderAs(List(4, 5, 1, 2, 3))
  }
}
