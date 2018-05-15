package wtf.scala.e06star.chess.solvers.benchmark

trait BenchmarkWithRightAnswerCheck {
  def rightAnswer: Int

  protected def assertRightAnswer(calculated: Int) = assert(calculated == rightAnswer, s"$calculated isn't right answer (!= $rightAnswer)")
}
