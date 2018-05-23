package wtf.scala.e06star

package object chess {

  type FieldSizeType = Int

  case class ChessProblem(width: FieldSizeType, height: FieldSizeType, pieces: Seq[ChessPiece])

  case class Point(x: FieldSizeType, y: FieldSizeType)

  case class ChessPiecePosition(piece: ChessPiece, p: Point)

  object ChessBoardState {
    def generateSafeCellsSet(problem: ChessProblem, pieces: Set[ChessPiecePosition]): Set[Point] = (for {
      x <- 0 until problem.width
      y <- 0 until problem.height
      if pieces.forall(piece => !(piece.p.x == x && piece.p.y == y) && !piece.piece.canHit(piece.p.x, piece.p.y, x, y))
    } yield Point(x, y)).toSet
  }

  case class ChessBoardState(pieces: Set[ChessPiecePosition], safeCells: Set[Point]) {
    def this(problem: ChessProblem, pieces: Set[ChessPiecePosition] = Set.empty) = {
      this(pieces, ChessBoardState.generateSafeCellsSet(problem, pieces))
    }

    def getSafePointsAfterPutPiece(newChessPiecePosition: ChessPiecePosition): Set[Point] =
      (safeCells - newChessPiecePosition.p).filter(p => !newChessPiecePosition.piece.canHit(newChessPiecePosition.p.x, newChessPiecePosition.p.y, p.x, p.y))
  }

  trait PositionGenerator extends (ChessProblem => Iterator[Set[ChessPiecePosition]])

  trait PositionChecker extends (Set[ChessPiecePosition] => Boolean)

  trait ChessProblemSolver extends PositionGenerator

  implicit object PiecesOrdered extends Ordering[ChessPiece] {
    private def pieceOrder(piece: ChessPiece): Int = piece match {
      case Queen => 1
      case Bishop => 2
      case Rook => 3
      case Knight => 4
      case King => 5
    }

    override def compare(x: ChessPiece, y: ChessPiece): Int = pieceOrder(x).compare(pieceOrder(y))
  }

}
