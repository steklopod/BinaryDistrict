package wtf.scala.e09.lecture

import akka.actor.{Actor, ActorLogging, ActorSystem, Kill, Props}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class HotSwapActor extends Actor with ActorLogging {
  import context._
  def angry: Receive = {
    case "foo" => log.info("I am already angry?")
    case "bar" => become(happy)
  }

  def happy: Receive = {
    case "bar" => log.info("I am already happy :-)")
    case "foo" => become(angry)
  }

  def receive = {
    case "foo" => become(angry)
    case "bar" => become(happy)
  }

  override def postStop(): Unit = {
    log.info("omg, i'm stopped")
  }
}

object HotSwapActorApp extends App {
  val system = ActorSystem("mySystem")
  val hotSwapActor = system.actorOf(Props[HotSwapActor])
  hotSwapActor ! "foo"
  hotSwapActor ! "foo"
  hotSwapActor ! "bar"
  hotSwapActor ! "bar"
  hotSwapActor ! "foo"
  hotSwapActor ! Kill
  Thread.sleep(1000)
  Await.ready(system.terminate(), Duration.Inf)
}
