package wtf.scala.e10.tcpchat

import akka.actor.{Actor, ActorRef, Props, ReceiveTimeout}
import akka.io.Tcp._
import akka.util.ByteString
import wtf.scala.e10.tcpchat.ChatManagerActor.{Message, _}

import scala.concurrent.duration._

object UserActor {
  def props(connection: ActorRef, chatManagerActor: ActorRef) = Props(new UserActor(connection, chatManagerActor))
}

class UserActor(connection: ActorRef, chatManagerActor: ActorRef) extends Actor {

  override def preStart(): Unit = {
    // start as anonymous
    context.become(anonymous)
    // send invitation to authorize
    connection ! Write(ByteString("Enter your nickname:\n"))
    // timeout for authorization
    context.setReceiveTimeout(30 seconds)
  }

  def authorized(nickname: String): Receive = {
    case Received(data) ⇒
      // todo parse message and send the command to chatManagerActor
      ???
    case m: Message =>
      // todo send the message to the connection (check akka tcp docs or tests)
      ???
    case ChatLog(messages) =>
      // todo send log messages as one message to the connection
      // todo don't send empty logs to the connection
      ???
    case ErrorMessage(message) =>
      // todo send error message to the connection started with '!'
      ???
    case PeerClosed ⇒
      // stop actor if disconnected
      context stop self
  }

  def anonymous: Receive = {
    case Received(data) ⇒
      // todo send "Hi, nickname\n" to the connection
      // todo change the state to authorized with that nickname if it's not empty after trimming
      ???
    case PeerClosed | ReceiveTimeout ⇒
      // stop if authorization timeout
      context stop self
  }

  def receive: Receive = Actor.emptyBehavior
}
