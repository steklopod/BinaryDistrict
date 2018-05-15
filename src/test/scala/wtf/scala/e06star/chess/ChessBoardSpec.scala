package wtf.scala.e06star.chess

import org.scalatest.{Matchers, WordSpec}

class ChessBoardSpec extends WordSpec with Matchers {

  "ChessBoardState.getSafePointsAfterPutPiece" should {
    "exclude hit points 1" in {
      val problem = ChessProblem(3, 3, Seq.empty)
      val newSafePositions = new ChessBoardState(problem).getSafePointsAfterPutPiece(ChessPiecePosition(King, Point(1, 1)))

      newSafePositions.size should be(0)
    }

    "exclude hit points 2" in {
      val problem = ChessProblem(4, 4, Seq.empty)
      val newSafePositions = new ChessBoardState(problem).getSafePointsAfterPutPiece(ChessPiecePosition(King, Point(1, 1)))

      newSafePositions.size should be(7)

      newSafePositions should contain(Point(0, 3))
      newSafePositions should contain(Point(1, 3))
      newSafePositions should contain(Point(2, 3))
      newSafePositions should contain(Point(3, 3))
      newSafePositions should contain(Point(3, 2))
      newSafePositions should contain(Point(3, 1))
      newSafePositions should contain(Point(3, 0))
    }
  }
}
