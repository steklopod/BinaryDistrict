package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Future6 extends App {
  val calcFuture = Future { Thread.sleep(1000); 1000 }
  val calcFailureFuture = Future { throw new RuntimeException("Error !!!") }
  calcFuture onComplete {
    case Success(i) =>
      println(i)
    case Failure(f) =>
      println(f.getMessage)
  }
//  deprecated calcFailureFuture onFailure
//  deprecated calcFailureFuture onSuccess
  Thread.sleep(2000)
}
