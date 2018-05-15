package wtf.scala.e06star.chess.solvers.benchmark

import wtf.scala.e06star.chess._
import wtf.scala.warmedTimed
import wtf.scala.e06star.chess.solvers.solvers.BruteForceSolver

// run it with -Xms4g -Xmx4g
// timeout about 10 minutes
object SolversBenchmarks extends App with BenchmarkWithRightAnswerCheck {
  val problem = new ChessProblem(5, 5, Seq(Rook, Rook, Knight, Knight, Knight, Knight))

  override final val rightAnswer = 1402

  val time = warmedTimed(10) {
    print(".")
    val solver = new BruteForceSolver(new SafePositionChecker)
    assertRightAnswer(solver(problem).size)
  }
  println
  println(s"Final result is $time ms")
}
