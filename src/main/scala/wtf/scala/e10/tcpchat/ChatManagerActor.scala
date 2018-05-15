package wtf.scala.e10.tcpchat

import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import fs2.internal.NonFatal

object ChatManagerActor {
  def props = Props(new ChatManagerActor())

  sealed trait ChatMessage

  case class Message(chat: String, nickname: String, msg: String) extends ChatMessage {
    override def toString: String = s"$chat:$nickname:$msg"
    def toMessageString: String = s"send:$chat:$msg\n"
  }
  case class EnterChat(chat: String, nickname: String) extends ChatMessage
  case class LeaveChat(chat: String, nickname: String) extends ChatMessage
  case class GetChatLog(chat: String) extends ChatMessage
  case class ChatLog(log: Seq[Message]) extends ChatMessage
  case class ErrorMessage(message: String) extends ChatMessage
}

class ChatManagerActor extends Actor with ActorLogging {
  import ChatManagerActor._

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case NonFatal(f) =>
      log.error(f, f.getMessage)
      SupervisorStrategy.resume
  }

  private def findOrCreateChat(chat: String): ActorRef = {
    // todo find child chat actor with that name or create it
    ???
  }

  override def receive: Receive = {
    case m@EnterChat(chat, nickname) =>
      findOrCreateChat(chat).forward(m)
    case m@LeaveChat(chat, nickname) =>
      findOrCreateChat(chat).forward(m)
    case m@Message(chat, nickname, message) =>
      findOrCreateChat(chat).forward(m)
    case m@GetChatLog(chat) =>
      findOrCreateChat(chat).forward(m)
  }
}
