package wtf.scala.e06star.chess

import wtf.scala.e06star.chess.solvers.solvers.BruteForceSolver

import scala.concurrent.duration._
import scala.io.StdIn._
import scala.util.{Failure, Success, Try}

object ChessProblemSolverApp {

  private def tryReadInt(message: String): Try[Int] = {
    print(message)
    Try {
      readInt()
    }.recoverWith { case e =>
      println("Incorrect input, please, enter a number")
      tryReadInt(message)
    }
  }

  private def tryReadPositiveNumber(message: String): Try[Int] = {
    print(message)
    Try {
      val i = readInt()
      if (i <= 0) {
        throw new IllegalArgumentException
      }
      i
    }.recoverWith { case e =>
      println("Incorrect input, please, enter a positive number")
      tryReadPositiveNumber(message)
    }
  }

  private def measureBlockTime(block: => Any): FiniteDuration = {
    val startMs = System.currentTimeMillis()
    block
    val endMs = System.currentTimeMillis()
    (endMs - startMs).millis
  }

  def calculateSolutions(problem: ChessProblem): FiniteDuration = (for {
    mode <- tryReadInt("Output only solutions number (1) or all solutions (2): ")
  } yield {
    val solver = new BruteForceSolver(new SafePositionChecker)
    mode match {
      case 1 =>
        measureBlockTime {
          println(s"Solutions number: ${solver(problem).size}")
        }
      case 2 =>
        measureBlockTime {
          var isResultEmpty = true
          solver(problem).zipWithIndex.foreach { case (resolution, index) =>
            isResultEmpty = false
            println(s"Solution #${index + 1}")
            ConsoleBoardVisualizer.visualize(resolution, problem)
          }
          if (isResultEmpty) println("There're no solution")
        }
      case _ =>
        throw new IllegalStateException("Incorrect calculation mode, please, retry input")
    }
  }).recover {
    case e =>
      println(e.getMessage)
      calculateSolutions(problem)
  }.get

  def main(args: Array[String]) {
    val problemTry = for {
      width <- tryReadPositiveNumber("Enter board width: ")
      height <- tryReadPositiveNumber("Enter board height: ")
      kings <- tryReadInt("Enter kings count: ")
      queens <- tryReadInt("Enter queens count: ")
      bishops <- tryReadInt("Enter bishops count: ")
      rooks <- tryReadInt("Enter rooks count: ")
      knights <- tryReadInt("Enter knights count: ")
    } yield {
      val problemPieces = Seq.fill(kings)(King) ++
        Seq.fill(queens)(Queen) ++
        Seq.fill(bishops)(Bishop) ++
        Seq.fill(rooks)(Rook) ++
        Seq.fill(knights)(Knight)

      if (problemPieces.isEmpty) throw new IllegalArgumentException("Please, enter at least one piece")

      new ChessProblem(width, height, problemPieces)
    }

    problemTry match {
      case Success(problem) =>
        val calculationTime = calculateSolutions(problem)
        println(s"Calculation and output time: $calculationTime")
      case Failure(f) =>
        println(s"Sorry, something goes wrong: '${f.getClass.getSimpleName}: ${f.getMessage}'")
    }

  }
}