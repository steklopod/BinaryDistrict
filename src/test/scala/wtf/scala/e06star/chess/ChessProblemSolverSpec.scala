package wtf.scala.e06star.chess

import org.scalatest.{Matchers, WordSpec}

trait ChessProblemSolverSpec extends WordSpec with Matchers {
  def solver: ChessProblemSolver

  val oddSymmetricalTestProblem = new ChessProblem(3, 3, Seq(King, King, Rook))
  val evenSymmetricalTestProblem = new ChessProblem(4, 4, Seq(Rook, Rook, Knight, Knight, Knight, Knight))
  val evenNotSymmetricalTestProblem = new ChessProblem(4, 2, Seq(King, Rook))
  val oddNotSymmetricalTestProblem = new ChessProblem(3, 2, Seq(King, Rook))

  "Solver" should {
    "solve odd symmetrical test problem" in {
      val result = solver(oddSymmetricalTestProblem).map(_.toSet).toSet
      result.size should be(4)
      result should contain(Set(ChessPiecePosition(King, Point(0, 0)), ChessPiecePosition(King, Point(0, 2)), ChessPiecePosition(Rook, Point(2, 1))))
      result should contain(Set(ChessPiecePosition(King, Point(0, 0)), ChessPiecePosition(King, Point(2, 0)), ChessPiecePosition(Rook, Point(1, 2))))
      result should contain(Set(ChessPiecePosition(King, Point(2, 0)), ChessPiecePosition(King, Point(2, 2)), ChessPiecePosition(Rook, Point(0, 1))))
      result should contain(Set(ChessPiecePosition(King, Point(0, 2)), ChessPiecePosition(King, Point(2, 2)), ChessPiecePosition(Rook, Point(1, 0))))
    }



    "solve even symmetrical test problem" in {
      val result = solver(evenSymmetricalTestProblem).map(_.toSet).toSet
      result.size should be(8)
      result should contain(Set(ChessPiecePosition(Rook, Point(0, 0)),
        ChessPiecePosition(Rook, Point(2, 2)),
        ChessPiecePosition(Knight, Point(3, 3)),
        ChessPiecePosition(Knight, Point(1, 1)),
        ChessPiecePosition(Knight, Point(3, 1)),
        ChessPiecePosition(Knight, Point(1, 3))))
      result should contain(Set(
        ChessPiecePosition(Rook, Point(3, 2)),
        ChessPiecePosition(Knight, Point(2, 1)),
        ChessPiecePosition(Knight, Point(0, 1)),
        ChessPiecePosition(Knight, Point(0, 3)),
        ChessPiecePosition(Rook, Point(1, 0)),
        ChessPiecePosition(Knight, Point(2, 3))
      ))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(0, 0)),
        ChessPiecePosition(Knight, Point(2, 2)),
        ChessPiecePosition(Knight, Point(0, 2)),
        ChessPiecePosition(Rook, Point(1, 3)),
        ChessPiecePosition(Rook, Point(3, 1)),
        ChessPiecePosition(Knight, Point(2, 0))))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(0, 0)),
        ChessPiecePosition(Rook, Point(3, 3)),
        ChessPiecePosition(Knight, Point(2, 2)),
        ChessPiecePosition(Rook, Point(1, 1)),
        ChessPiecePosition(Knight, Point(0, 2)),
        ChessPiecePosition(Knight, Point(2, 0))))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(1, 0)),
        ChessPiecePosition(Knight, Point(3, 2)),
        ChessPiecePosition(Knight, Point(1, 2)),
        ChessPiecePosition(Knight, Point(3, 0)),
        ChessPiecePosition(Rook, Point(0, 3)),
        ChessPiecePosition(Rook, Point(2, 1))
      ))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(2, 1)),
        ChessPiecePosition(Knight, Point(0, 1)),
        ChessPiecePosition(Rook, Point(1, 2)),
        ChessPiecePosition(Knight, Point(0, 3)),
        ChessPiecePosition(Knight, Point(2, 3)),
        ChessPiecePosition(Rook, Point(3, 0))
      ))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(3, 3)),
        ChessPiecePosition(Knight, Point(1, 1)),
        ChessPiecePosition(Knight, Point(3, 1)),
        ChessPiecePosition(Knight, Point(1, 3)),
        ChessPiecePosition(Rook, Point(2, 0)),
        ChessPiecePosition(Rook, Point(0, 2))
      ))
      result should contain(Set(
        ChessPiecePosition(Knight, Point(1, 0)),
        ChessPiecePosition(Knight, Point(3, 2)),
        ChessPiecePosition(Knight, Point(1, 2)),
        ChessPiecePosition(Knight, Point(3, 0)),
        ChessPiecePosition(Rook, Point(2, 3)),
        ChessPiecePosition(Rook, Point(0, 1))
      ))
    }

    "solve even not symmetrical test problem" in {
      val result = solver(evenNotSymmetricalTestProblem).map(_.toSet).toSet

      result.size should be(12)

      result should contain(Set(ChessPiecePosition(King, Point(0, 0)),
        ChessPiecePosition(Rook, Point(2, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(2, 1)),
        ChessPiecePosition(Rook, Point(0, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(3, 0)),
        ChessPiecePosition(Rook, Point(1, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(3, 1)),
        ChessPiecePosition(Rook, Point(1, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(0, 0)),
        ChessPiecePosition(Rook, Point(3, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(1, 1)),
        ChessPiecePosition(Rook, Point(3, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(2, 0)),
        ChessPiecePosition(Rook, Point(0, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(0, 1)),
        ChessPiecePosition(Rook, Point(3, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(1, 0)),
        ChessPiecePosition(Rook, Point(3, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(3, 0)),
        ChessPiecePosition(Rook, Point(0, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(0, 1)),
        ChessPiecePosition(Rook, Point(2, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(3, 1)),
        ChessPiecePosition(Rook, Point(0, 0))))
    }

    "solve odd not symmetrical test problem" in {
      val result = solver(oddNotSymmetricalTestProblem).map(_.toSet).toSet

      result should contain(Set(ChessPiecePosition(King, Point(0, 0)),
        ChessPiecePosition(Rook, Point(2, 1))))

      result should contain(Set(ChessPiecePosition(King, Point(0, 1)),
        ChessPiecePosition(Rook, Point(2, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(2, 1)),
        ChessPiecePosition(Rook, Point(0, 0))))

      result should contain(Set(ChessPiecePosition(King, Point(2, 0)),
        ChessPiecePosition(Rook, Point(0, 1))))

      result.size should be(4)
    }
  }
}