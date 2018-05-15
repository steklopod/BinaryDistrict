package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

object Promise2 extends App {
  val f = Future { 1 }
  val p = Promise[Int]()
  p tryCompleteWith f
  println(p trySuccess 2)
  println(p tryFailure new RuntimeException)
  p.future onSuccess {
    case x => println(x)
  }
  Thread.sleep(1000)
}
