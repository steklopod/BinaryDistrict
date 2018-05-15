package wtf.scala.e06star.chess.solvers.benchmark

import wtf.scala.e06star.chess._
import wtf.scala.e06star.chess.solvers.solvers.BruteForceSolver
import wtf.scala.warmedTimed

// run it with -Xms4g -Xmx4g
// timeout about 10 minutes
object ChallengeProblemSolutionBenchmark extends App with BenchmarkWithRightAnswerCheck  {
  override final val rightAnswer = 3063828

  val problem = new ChessProblem(7, 7, Seq(King, King, Queen, Queen, Bishop, Bishop, Knight))

  val time = warmedTimed(10) {
    print(".")
    val solver = new BruteForceSolver(new SafePositionChecker)
    assertRightAnswer(solver(problem).size)
  }
  println
  println(s"Final result is $time ms")
}
