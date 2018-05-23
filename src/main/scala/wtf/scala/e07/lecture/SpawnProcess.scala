package wtf.scala.e07.lecture

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source

object ThreadApp extends App {
  def spawn(command: String): Future[String] = Future {
    val process = sys.runtime.exec(command)
    Source.fromInputStream(process.getInputStream).getLines().mkString("\n")
  }

  val res = Await.result(spawn("ping ya.ru -t 2"), Duration.Inf)
  println(res)
}
