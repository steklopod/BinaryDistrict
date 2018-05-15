package wtf.scala.e09

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Echoes in the night
  *
  * In this exercise we will learn how to create our first Akka Actor,
  * the "EchoActor", the purpose of this Actor is to echo back incoming messages
  * to whomever sent the message. If there is way to reply, it should do nothing.
  *
  * We will learn how to create Actors, start them, receive messages and reply with a response.
  */
object EchoActor {
  val props: Props = Props[EchoActor]
}

class EchoActor extends Actor {
  override def receive: Receive = ???
}

object EchoActorApp extends App {
  val actorSystem = ActorSystem("app")
  val echoActor = actorSystem.actorOf(EchoActor.props)
  implicit val timeout: Timeout = Timeout(1 second)

  val answer1F = echoActor ? "Some msg"
  val answer2F = echoActor ? "Some other msg"

  for {
    answer1 <- answer1F
    answer2 <- answer2F
    _ <- {
      println(answer1, answer2)
      actorSystem.terminate()
    }
  } yield ()
}