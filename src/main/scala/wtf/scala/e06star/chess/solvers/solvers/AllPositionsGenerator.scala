package wtf.scala.e06star.chess.solvers.solvers

import wtf.scala.e06star.chess._

class AllPositionsGenerator extends PositionGenerator {
  override def apply(problem: ChessProblem): Iterator[Set[ChessPiecePosition]] = {
    val totalPieces = problem.pieces.length
    val flatBoard = problem.pieces.map(Some.apply).toStream ++ Stream.fill(problem.width * problem.height - totalPieces)(None)
    val flatBoardPermutations = flatBoard.permutations
    val boardPermutations = flatBoardPermutations.map(_.grouped(problem.width))
    for {boardPermutation <- boardPermutations}
      yield {
        for {(col, y) <- boardPermutation.zipWithIndex.toSet
             (elOpt, x) <- col.zipWithIndex
             el <- elOpt
        } yield {
          ChessPiecePosition(el, Point(x, y))
        }
      }
  }
}