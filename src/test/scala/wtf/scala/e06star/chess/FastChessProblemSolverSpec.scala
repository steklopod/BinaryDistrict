package wtf.scala.e06star.chess

trait FastChessProblemSolverSpec extends ChessProblemSolverSpec {

  val eightQueensProblem = new ChessProblem(8, 8, Seq.fill(8)(Queen))
  val bigProblem = new ChessProblem(8, 8, Seq(Knight, Knight, Queen, King))

  "FastChessProblemSolverSpec" should {
    "solve eight queens problem" in {
      val result = solver(eightQueensProblem)
      result.size should be(92)
    }

    "solve problems with large number of unique solutions" in {
      val result = solver(bigProblem)
      result.size should be(861816)
    }
  }
}
