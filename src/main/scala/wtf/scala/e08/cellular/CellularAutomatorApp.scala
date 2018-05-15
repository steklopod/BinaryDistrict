package wtf.scala.e08.cellular

import java.util.concurrent.Executors

import wtf.scala._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}
import scala.io.StdIn
import scala.util.{Success, Try}

object CellularAutomatorApp extends App {
  val singleThreadAutomator = new SingleThreadOneDimentionCellularAutomator(OneDimentionCellularAutomator.Rule30, 1)

  val processors = Runtime.getRuntime.availableProcessors()

  val pool = Executors.newFixedThreadPool(processors * 2)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)
  val futureMultiThreadAutomator = new MultiThreadOneDimentionCellularAutomator(50, OneDimentionCellularAutomator.Rule30, 1)

  val startState = {
    val size = 20000
    Seq.fill(size)(false).updated(size / 2, true)
  }

  val steps = 100
  val warmUps = 10

  print("Warmup single thread")
  val singleThreadTime = warmedTimed(warmUps) {
    print(".")
    Await.result(singleThreadAutomator.calculateNextStates(startState, steps).map(_.toList), Duration.Inf)
  }
  println

  println(s"Single thread calculation time: $singleThreadTime")

  print("Warmup future multi thread")
  val futureMultiThreadTime = warmedTimed(warmUps) {
    print(".")
    Await.result(futureMultiThreadAutomator.calculateNextStates(startState, steps).map(_.toList), Duration.Inf)
  }
  println

  println(s"Future multi thread calculation time: $futureMultiThreadTime")

  println("Print result to console? [y/n]")
  Try(StdIn.readChar()) match {
    case Success('y') =>
      val result = Await.result(futureMultiThreadAutomator.calculateNextStates(startState, steps), Duration.Inf)
      result.foreach(field => println(field.map(v => if (v) '▉' else ' ').mkString))
    case _ =>
  }

  println("Save result to PNG file? [y/n]")
  Try(StdIn.readChar()) match {
    case Success('y') =>
      val result = Await.result(futureMultiThreadAutomator.calculateNextStates(startState, steps), Duration.Inf)
      val fileName = "cellular-automaton.png"
      new CellularAutomatorImageSaver("PNG", fileName).apply(result, startState.length, steps)
      println(s"See '$fileName' file with cellular automaton evolution image in project folder")
    case _ =>
  }

  pool.shutdown()
}
