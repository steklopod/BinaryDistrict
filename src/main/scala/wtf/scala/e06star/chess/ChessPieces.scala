package wtf.scala.e06star.chess

sealed trait ChessPiece {
  def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean

  def generateNextSafePositions(currentState: ChessBoardState): Iterator[ChessPiecePosition] = ???
}

object King extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = ???
}

object Queen extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = ???
}

object Bishop extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = ???
}

object Rook extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = ???
}

object Knight extends ChessPiece {
  override def canHit(pieceX: Int, pieceY: Int, checkX: Int, checkY: Int): Boolean = ???
}