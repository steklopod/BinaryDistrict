package wtf.scala.e10.tcpchat

import akka.actor.{Actor, ActorRef, Props}
import akka.io.Tcp._

object ConnectionManagerActor {
  def props(chatManagerActor: ActorRef) = Props(new ConnectionManagerActor(chatManagerActor))
}
class ConnectionManagerActor(chatManagerActor: ActorRef) extends Actor {
  override def receive: Receive = {
    case b @ Bound(localAddress) ⇒
      context.parent ! b

    case CommandFailed(_: Bind) ⇒ context stop self

    case c @ Connected(remote, local) ⇒
      val connection = sender()
      val handler = context.actorOf(UserActor.props(connection, chatManagerActor))
      connection ! Register(handler)
  }
}
