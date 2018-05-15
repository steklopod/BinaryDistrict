package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future2 extends App {
  val calcFuture = Future {
    Thread.sleep(1000)
    1000
  }
  val stringFuture = calcFuture.map { i =>
    s"oh, it is $i!"
  }
  val string = Await.result(stringFuture, Duration.Inf)
  println(string)
}
