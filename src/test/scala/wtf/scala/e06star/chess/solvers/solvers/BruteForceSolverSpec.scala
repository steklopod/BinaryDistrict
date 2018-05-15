package wtf.scala.e06star.chess.solvers.solvers

import wtf.scala.e06star.chess.{ChessProblemSolverSpec, SafePositionChecker}

class BruteForceSolverSpec extends ChessProblemSolverSpec {
  override val solver = new BruteForceSolver(new SafePositionChecker)
}
