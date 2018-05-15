package wtf.scala.e06star.chess

import org.scalatest.{Matchers, WordSpec}

class ChessPiecesSpec extends WordSpec with Matchers {
  private def hitPositionsCount(piece: ChessPiece, pieceX: Int, pieceY: Int, width: Int, height: Int): Int = {
    val hitMap = for {
      x <- 0 until width
      y <- 0 until height
    } yield {
      piece.canHit(pieceX, pieceX, x, y)
    }
    val hitPositionsCount = hitMap.count(h => h)
    hitPositionsCount
  }

  "King" should {
    "have the correct hit positions" in {
      King.canHit(1, 1, 0, 0) should be(true)
      King.canHit(1, 1, 0, 1) should be(true)
      King.canHit(1, 1, 0, 2) should be(true)
      King.canHit(1, 1, 1, 0) should be(true)
      King.canHit(1, 1, 1, 2) should be(true)
      King.canHit(1, 1, 2, 0) should be(true)
      King.canHit(1, 1, 2, 1) should be(true)
      King.canHit(1, 1, 2, 2) should be(true)
    }

    "have the correct hit positions count" in {
      hitPositionsCount(King, 4, 4, 9, 9) should be(8)
    }
  }

  "Queen" should {
    "have the correct hit positions" in {
      Queen.canHit(4, 4, 0, 0) should be(true)
      Queen.canHit(4, 4, 8, 8) should be(true)
      Queen.canHit(4, 4, 0, 8) should be(true)
      Queen.canHit(4, 4, 8, 0) should be(true)
      Queen.canHit(4, 4, 4, 8) should be(true)
      Queen.canHit(4, 4, 8, 4) should be(true)
      Queen.canHit(4, 4, 4, 0) should be(true)
      Queen.canHit(4, 4, 0, 4) should be(true)
    }

    "have the correct hit positions count" in {
      hitPositionsCount(Queen, 4, 4, 9, 9) should be(32)
    }
  }

  "Bishop" should {
    "have the correct hit positions" in {
      Bishop.canHit(4, 4, 0, 0) should be(true)
      Bishop.canHit(4, 4, 8, 8) should be(true)
      Bishop.canHit(4, 4, 0, 8) should be(true)
      Bishop.canHit(4, 4, 8, 0) should be(true)
    }

    "have the correct hit positions count" in {
      hitPositionsCount(Bishop, 4, 4, 9, 9) should be(16)
    }
  }

  "Rook" should {
    "have the correct hit positions" in {
      Rook.canHit(4, 4, 4, 8) should be(true)
      Rook.canHit(4, 4, 8, 4) should be(true)
      Rook.canHit(4, 4, 4, 0) should be(true)
      Rook.canHit(4, 4, 0, 4) should be(true)
    }

    "have the correct hit positions count" in {
      hitPositionsCount(Rook, 4, 4, 9, 9) should be(16)
    }
  }

  "Knight" should {
    "have the correct hit positions" in {
      Knight.canHit(0, 0, 1, 2) should be(true)
      Knight.canHit(0, 0, 1, -2) should be(true)
      Knight.canHit(0, 0, 2, 1) should be(true)
      Knight.canHit(0, 0, 2, -1) should be(true)

      Knight.canHit(0, 0, -1, 2) should be(true)
      Knight.canHit(0, 0, -1, -2) should be(true)
      Knight.canHit(0, 0, -2, 1) should be(true)
      Knight.canHit(0, 0, -2, -1) should be(true)
    }

    "have the correct hit positions count" in {
      hitPositionsCount(Knight, 4, 4, 9, 9) should be(8)
    }
  }

  "Piece.generateNextSafePositions" should {
    "generate unique positions with empty current steps" in {

      val problem = ChessProblem(3, 3, Seq.empty)
      val generatedPositions = King.generateNextSafePositions(new ChessBoardState(problem)).toSeq

      generatedPositions should contain(ChessPiecePosition(King, Point(2, 1)))
      generatedPositions should contain(ChessPiecePosition(King, Point(1, 2)))
      generatedPositions should contain(ChessPiecePosition(King, Point(2, 2)))
      generatedPositions should contain(ChessPiecePosition(King, Point(0, 2)))
      generatedPositions should contain(ChessPiecePosition(King, Point(2, 0)))
      generatedPositions should contain(ChessPiecePosition(King, Point(0, 1)))
      generatedPositions should contain(ChessPiecePosition(King, Point(0, 0)))
      generatedPositions should contain(ChessPiecePosition(King, Point(1, 0)))
      generatedPositions should contain(ChessPiecePosition(King, Point(1, 1)))

      generatedPositions.size should be(9)
    }

    "generate unique positions with non-empty current steps" in {
      val problem = ChessProblem(3, 3, Seq.empty)
      val generatedPositions = King.generateNextSafePositions(new ChessBoardState(problem, Set(ChessPiecePosition(King, Point(0, 0))))).toSeq

      generatedPositions.size should be(5)

      generatedPositions should contain(ChessPiecePosition(King, Point(2, 1)))
      generatedPositions should contain(ChessPiecePosition(King, Point(1, 2)))
      generatedPositions should contain(ChessPiecePosition(King, Point(0, 2)))
      generatedPositions should contain(ChessPiecePosition(King, Point(2, 0)))
      generatedPositions should contain(ChessPiecePosition(King, Point(2, 2)))
    }

    "generate unique positions if no safe steps" in {
      val problem = ChessProblem(3, 3, Seq.empty)
      val generatedPositions = King.generateNextSafePositions(new ChessBoardState(problem, Set(ChessPiecePosition(King, Point(1, 1))))).toSeq

      generatedPositions.size should be(0)
    }
  }
}
