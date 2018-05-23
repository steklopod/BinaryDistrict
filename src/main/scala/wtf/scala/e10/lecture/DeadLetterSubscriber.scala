package wtf.scala.e10.lecture

import akka.actor.{Actor, ActorSystem, DeadLetter, PoisonPill, Props}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Listener extends Actor {
  def receive: Receive = {
    case d: DeadLetter => println(d)
  }
}

object DeadLetterSubscriberApp extends App {
  val system = ActorSystem("mySystem")
  val listener = system.actorOf(Props(classOf[Listener]))
  system.eventStream.subscribe(listener, classOf[DeadLetter])
  val secondListener = system.actorOf(Props(classOf[Listener]))
  secondListener ! PoisonPill
  // it goes to dead letters
  secondListener ! "!11"

  Thread.sleep(1000)
  Await.ready(system.terminate(), Duration.Inf)
}
