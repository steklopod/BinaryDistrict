package wtf.scala.e10.tcpchat

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.io.Tcp.Bind
import akka.io.{IO, Tcp}
import akka.util.Timeout
import akka.pattern._

import scala.concurrent.duration._

object ChatServer extends App {
  implicit val actorSystem: ActorSystem = ActorSystem("app")
  implicit val timeout: Timeout = Timeout(1 second)
  val chatManagerActor = actorSystem.actorOf(ChatManagerActor.props)
  val connectionManagerActor = actorSystem.actorOf(ConnectionManagerActor.props(chatManagerActor))

  val bindF = IO(Tcp) ? Bind(connectionManagerActor, new InetSocketAddress("localhost", 8290))
}
