package wtf.scala.e06star.chess

trait ChessBoardVisualizer {
  def visualize(board: Iterable[ChessPiecePosition], problem: ChessProblem): Unit
}

object ConsoleBoardVisualizer extends ChessBoardVisualizer {
  def visualize(boardState: Iterable[ChessPiecePosition], problem: ChessProblem): Unit = {
    for {x <- 0 until problem.width} {
      for {
        y <- 0 until  problem.height
      } {
        val positionPieceOption = boardState.find(piece => piece.p.x == x && piece.p.y == y).map(_.piece)
        val textRepresentation = positionPieceOption match {
          case None => ". "
          case Some(Knight) => "♞ "
          case Some(King) => "♚ "
          case Some(Bishop) => "♝ "
          case Some(Rook) => "♜ "
          case Some(Queen) => "♛ "
        }
        print(textRepresentation)
      }
      println()
    }
    println()
  }
}