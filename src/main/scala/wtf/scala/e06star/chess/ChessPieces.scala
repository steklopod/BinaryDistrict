package wtf.scala.e06star.chess

sealed trait ChessPiece {
  def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean

  def generateNextSafePositions(currentState: ChessBoardState): Iterator[ChessPiecePosition] = {
    for {
      freePiece <- currentState.safeCells.iterator
      newPiecePosition = ChessPiecePosition(this, Point(freePiece.x, freePiece.y))
      if currentState.pieces.forall(placedPiece =>
        !canHit(placedPiece.p.x, placedPiece.p.y, freePiece.x, freePiece.y) &&
          !placedPiece.piece.canHit(placedPiece.p.x, placedPiece.p.y, freePiece.x, freePiece.y))
    } yield newPiecePosition
  }
}

object King extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = {
    !(pieceX == checkX && pieceY == checkY) &&
      ((checkX - pieceX).abs <= 1 && (checkY - pieceY).abs <= 1)
  }
}

object Queen extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = {
    !(pieceX == checkX && pieceY == checkY) &&
      (pieceX == checkX || pieceY == checkY || (checkX - pieceX).abs == (checkY - pieceY).abs)
  }
}

object Bishop extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = {
    !(pieceX == checkX && pieceY == checkY) &&
      ((checkX - pieceX).abs == (checkY - pieceY).abs)
  }
}

object Rook extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = {
    !(pieceX == checkX && pieceY == checkY) &&
      (pieceX == checkX || pieceY == checkY)
  }
}

object Knight extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = {
    !(pieceX == checkX && pieceY == checkY) &&
      ((checkX - pieceX).abs <= 2 && (checkY - pieceY).abs <= 2) &&
      ((pieceX == checkX + 1 && pieceY == checkY + 2) ||
        (pieceX == checkX + 1 && pieceY == checkY - 2) ||
        (pieceX == checkX + 2 && pieceY == checkY + 1) ||
        (pieceX == checkX + 2 && pieceY == checkY - 1) ||
        (pieceX == checkX - 1 && pieceY == checkY + 2) ||
        (pieceX == checkX - 1 && pieceY == checkY - 2) ||
        (pieceX == checkX - 2 && pieceY == checkY + 1) ||
        (pieceX == checkX - 2 && pieceY == checkY - 1)
        )
  }
}