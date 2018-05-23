package wtf.scala.e06star.chess

class SafePositionChecker extends PositionChecker {
  override def apply(board: Set[ChessPiecePosition]): Boolean = {
    val piecesChechCombinations = board.toSeq.combinations(2)
    piecesChechCombinations.forall {
      case Seq(firstPiece, secondPiece) =>
        !(firstPiece.p.x == secondPiece.p.x && firstPiece.p.y == secondPiece.p.y) &&
        !firstPiece.piece.canHit(firstPiece.p.x, firstPiece.p.y, secondPiece.p.x, secondPiece.p.y) &&
          !secondPiece.piece.canHit(secondPiece.p.x, secondPiece.p.y, firstPiece.p.x, firstPiece.p.y)
    }
  }
}