package wtf.scala.e09.lecture

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import wtf.scala.e09.lecture.MyActor.{Goodbye, Greeting}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object MyActor {
  case class Greeting(from: String)
  case object Goodbye
}
class MyActor extends Actor with ActorLogging {
  import MyActor._
  def receive: Receive = {
    case Greeting(greeter) => log.info(s"I was greeted by $greeter.")
    case Goodbye           => log.info("Someone said goodbye to me.")
  }
}
object MyActorApp extends App {
  val system = ActorSystem("mySystem")
  val myActor = system.actorOf(Props[MyActor], "myactor")
  myActor ! Greeting("me")
  myActor ! Greeting("you")
  myActor ! Goodbye
  Await.ready(system.terminate(), Duration.Inf)
}
