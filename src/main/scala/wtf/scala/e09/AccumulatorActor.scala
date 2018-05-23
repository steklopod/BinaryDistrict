package wtf.scala.e09

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import AccumulatorActor._

/**
  * Akkumulator
  *
  * In this exercise we will learn how to manage state within an Actor, and how to handle different messages
  * within the same Actor.
  */
object AccumulatorActor {
  val props: Props = Props[AccumulatorActor]
  case class Set(value: Int)
  case class Add(value: Int)
  case object Get
}

class AccumulatorActor extends Actor {
  private var value: Int = 0
  override def receive: Receive = {
    case Set(v) => value = v
    case Add(v) => value += v
    case Get => sender() ! value
  }
}

object AccumulatorActorApp extends App {
  val actorSystem = ActorSystem("app")
  val accumulatorActor = actorSystem.actorOf(AccumulatorActor.props)
  implicit val timeout: Timeout = Timeout(1 second)

  accumulatorActor ! Set(1)
  accumulatorActor ! Add(1)
  val getF = accumulatorActor ? Get

  for {
    result <- getF
    _ <- {
      println(result)
      actorSystem.terminate()
    }
  } yield ()
}