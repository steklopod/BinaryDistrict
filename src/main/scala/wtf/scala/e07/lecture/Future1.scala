package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future1 extends App {
  val printFuture = Future {
    println("Hello from future")
  }
  Await.result(printFuture, Duration.Inf)
}
