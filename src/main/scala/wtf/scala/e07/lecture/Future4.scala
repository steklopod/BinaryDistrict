package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future4 extends App {
  val calcFuture = Future { Thread.sleep(1000); 1000 }
  def stringFuture(i: Int) = Future { s"$i!!!" }
  val f = for {
    calcResult <- calcFuture
    stringResult <- stringFuture(calcResult)
  } yield {
    println(s"$calcResult $stringResult")
  }
  Await.result(f, Duration.Inf)
}
