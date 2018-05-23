package wtf.scala.e11.lecture

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

object SimpleStreamExample extends App {
  val source: Source[Int, NotUsed] = Source(1 to 100)
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  implicit val ec = ExecutionContext.global
  source.runForeach(i => println(i))(materializer).onComplete(_ => Await.result(system.terminate(), Duration.Inf))
}
