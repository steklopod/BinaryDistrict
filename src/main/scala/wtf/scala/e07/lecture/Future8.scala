package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Future8 extends App {
  val futures = for { i <- 1 to 10 } yield Future.successful(i)
  val r1 = Future.sequence(futures)
  val r2 = Future.sequence(futures :+ Future.failed(new RuntimeException))
  println(r1.value)
  println(r2.value)
}
