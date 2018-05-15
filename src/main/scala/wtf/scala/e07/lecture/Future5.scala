package wtf.scala.e07.lecture

import java.util.concurrent.Executors

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Future5 extends App {
  val e1 = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
  implicit val e2 = ExecutionContext.fromExecutor(Executors.newWorkStealingPool())

  val calcFuture = Future { Thread.sleep(1000); 1000 }(e1)
  val stringFuture = Future { Thread.sleep(1000); "Hello from second future"}(e2)

  val f = for {
    calcResult <- calcFuture
    stringResult <- stringFuture
  } yield {
    println(s"$calcResult $stringResult")
  }

  Await.result(f, Duration.Inf)
}
