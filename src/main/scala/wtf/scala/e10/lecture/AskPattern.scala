package wtf.scala.e10.lecture

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class AnswerActor extends Actor {
  override def receive: Receive = {
    case message => sender() ! message
  }
}

object AskPattern extends App {
  val system = ActorSystem("mySystem")
  val actor = system.actorOf(Props[AnswerActor])
  implicit val timeout = Timeout(2.seconds)
  implicit val ec = ExecutionContext.global
  val f = for {
    answer <- actor ? "one"
    answerTwo <- actor ? "two"
    _ <- system.terminate()
  } yield {
    println(answer)
    println(answerTwo)
  }
  Await.result(f, Duration.Inf)
}
