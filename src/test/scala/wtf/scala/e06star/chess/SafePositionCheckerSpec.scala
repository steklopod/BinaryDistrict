package wtf.scala.e06star.chess

import org.scalatest.{Matchers, WordSpec}

class SafePositionCheckerSpec extends WordSpec with Matchers {

  val safePositionChecker = new SafePositionChecker()

  "SafePositionChecker" should {
    "identify the safe chess pieces positions" in {
      val safeBoard = Set(ChessPiecePosition(King, Point(0, 0)), ChessPiecePosition(King, Point(2, 2)))
      safePositionChecker(safeBoard) should be(true)
    }

    "identify the hit chess pieces positions" in {
      val safeBoard = Set(ChessPiecePosition(King, Point(0, 0)), ChessPiecePosition(King, Point(1, 1)))
      safePositionChecker(safeBoard) should be(false)
    }
  }
}
