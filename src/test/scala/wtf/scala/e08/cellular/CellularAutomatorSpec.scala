package wtf.scala.e08.cellular

import java.util.concurrent.Executors

import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor}

class CellularAutomatorSpec extends FunSuite with Matchers {
  val processors = Runtime.getRuntime.availableProcessors()
  val pool = Executors.newFixedThreadPool(processors * 4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

  test("multi thread cellular automator generator should generate a valid rule30 result") {
    val startState = {
      val size = 5000
      Seq.fill(size)(false).updated(size / 2, true)
    }
    val steps = 20

    val singleThreadAutomator = new SingleThreadOneDimentionCellularAutomator(OneDimentionCellularAutomator.Rule30, 1)
    val multiThreadAutomator = new MultiThreadOneDimentionCellularAutomator(10, OneDimentionCellularAutomator.Rule30, 1)

    val singleThreadResults = Await.result(singleThreadAutomator.calculateNextStates(startState, steps), Duration.Inf)
    val multiThreadResults = Await.result(multiThreadAutomator.calculateNextStates(startState, steps), Duration.Inf)

    singleThreadResults should be(multiThreadResults)
  }
}
