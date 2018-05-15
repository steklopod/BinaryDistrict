package wtf.scala.e07.lecture

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

object Promise1 extends App {
  val p = Promise[Int]()
  val f = p.future
  val producer = Future {
    p success 1
  }
  val consumer = Future {
    f onSuccess {
      case r => println(r)
    }
  }
  Thread.sleep(1000)
}
