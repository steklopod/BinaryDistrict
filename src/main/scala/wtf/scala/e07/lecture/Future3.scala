package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future3 extends App {
  val calcFuture = Future {
    Thread.sleep(1000)
    1000
  }

  val stringFuture = Future {
    "Hello from second future"
  }

  val f = for {
    calcResult <- calcFuture
    stringResult <- stringFuture
  } yield {
    println(s"$calcResult $stringResult")
  }

  Await.result(f, Duration.Inf)
}
