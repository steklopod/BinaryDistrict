package wtf.scala.e10.lecture

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.dispatch.ExecutionContexts
import akka.pattern._

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

class StupidActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case "oops" => throw new RuntimeException("oops")
  }

  override def preStart(): Unit = {
    log.info(s"prestart!")
  }
}

object AkkaPatternApp extends App {
  val system = ActorSystem("mySystem")

  val actorSupervisorProps = BackoffSupervisor.props(
    Backoff.onFailure(
      Props[StupidActor],
      childName = "stupid",
      minBackoff = 1.seconds,
      maxBackoff = 30.seconds,
      randomFactor = 0.2 // adds 20% "noise" to vary the intervals slightly
    ))

  val actorSupervisor = system.actorOf(actorSupervisorProps, name = "actorSupervisor")

  actorSupervisor ! "oops"
  // it goes to dead letters
  actorSupervisor ! "oops"
  Thread.sleep(5000)
  actorSupervisor ! "oops"
  Thread.sleep(15000)
  actorSupervisor ! "oops"

  import akka.pattern.after

  implicit val ec = ExecutionContext.global
  val afterFuture = after(1 second, system.scheduler) {
    Future {
      Thread.sleep(300)
      1
    }
  }

  Await.result(gracefulStop(actorSupervisor, 1 second), 1 second)
  Await.result(afterFuture, 2 seconds)
  Await.ready(system.terminate(), Duration.Inf)
}
