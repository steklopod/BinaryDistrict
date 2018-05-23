package wtf.scala.e09.lecture

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, DiagnosticActorLogging, Props}
import wtf.scala.e09.lecture.Forwarder.Forward

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Forwarder {
  case class Forward(f: Any)

  def props(to: ActorRef): Props = Props(new Forwarder(to))
}
class Forwarder(to: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case Forward(smnt) =>
      to ! smnt
    case answer =>
      log.info(s"${context.self.path} got $answer")
  }
}

class SenderPrint extends Actor with ActorLogging {
  override def receive: Receive = {
    case smnt =>
      log.info(s"${sender()} send $smnt to me")
      sender() ! "hello"
  }
}
object SenderPrintApp extends App {
  val system = ActorSystem("mySystem")
  val myActor = system.actorOf(Props[SenderPrint], "sender-print-actor")
  val forwarderActor = system.actorOf(Forwarder.props(myActor), "sender-print-actor-forwarder")

  // answers will sended to deadLetters
  myActor ! 1
  myActor ! "ultra important message"

  // forwarder will receive the answers
  forwarderActor ! Forward(1)
  forwarderActor ! Forward("ultra important message")

  Await.ready(system.terminate(), Duration.Inf)
}
