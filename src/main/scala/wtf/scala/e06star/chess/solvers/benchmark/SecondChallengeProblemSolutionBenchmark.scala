package wtf.scala.e06star.chess.solvers.benchmark

import wtf.scala.e06star.chess._
import wtf.scala.e06star.chess.solvers.solvers.BruteForceSolver
import wtf.scala.warmedTimed

// run it with -Xms4g -Xmx4g
// timeout about 30 minutes
object SecondChallengeProblemSolutionBenchmark extends BenchmarkWithRightAnswerCheck {
  override final val rightAnswer = 20136752

  val problem = new ChessProblem(6, 9, Seq(King, King, Queen, Rook, Bishop, Knight))

  val time = warmedTimed(10) {
    print(".")
    val solver = new BruteForceSolver(new SafePositionChecker)
    assertRightAnswer(solver(problem).size)
  }
  println
  println(s"Final result is $time ms")
}
