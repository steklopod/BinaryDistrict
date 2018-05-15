package wtf.scala.e06star.chess.solvers.solvers

import wtf.scala.e06star.chess._


class BruteForceSolver(positionChecker: PositionChecker) extends ChessProblemSolver {
  val allPositionGenerator = new AllPositionsGenerator()

  // iterating over all possible positions and filter only safe
  override def apply(problem: ChessProblem): Iterator[Set[ChessPiecePosition]] = allPositionGenerator(problem).filter(positionChecker)
}