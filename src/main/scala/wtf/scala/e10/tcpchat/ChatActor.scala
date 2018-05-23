package wtf.scala.e10.tcpchat

import akka.actor._
import wtf.scala.e10.tcpchat.ChatManagerActor._

/**
  * The Chattering
  *
  * In this exercise we will collaborate with others to create a simple, distributed, chat.
  */
object ChatActor {
  def props = Props(new ChatActor())
}

class ChatActor extends Actor {

  private var last10Log = Vector.empty[Message]
  private var connected = Map.empty[String, ActorRef]

  def testChatName(chatName: String)(b: => Unit): Unit = {
    if (chatName == context.self.path.name) {
      b
    } else {
      sender() ! ErrorMessage("Wrong chat name")
    }
  }

  def receive: PartialFunction[Any, Unit] = {
    case LeaveChat(chat, nickname) =>
      //TODO: only if sent to this chat
      //TODO: only if sender is connected to this chat with that username else answer with ErrorMessage
      testChatName(chat) {
        if (connected.contains(nickname)) {
          val isSameActorConnected = connected.get(nickname).contains(sender())
          if (isSameActorConnected) {
            connected = connected - nickname
          } else {
            sender() ! ErrorMessage(s"$nickname connected as different actor")
          }
        } else {
          sender() ! ErrorMessage(s"$nickname isn't in this chat")
        }
      }
    case EnterChat(chat, nickname) =>
      //TODO: only if sent to this chat
      //TODO: only if sender is not connected to this chat else answer with ErrorMessage
      testChatName(chat) {
        val differentNickname = connected.find(_._2 == sender()).map(_._1)
        if (connected.contains(nickname) || differentNickname.isDefined) {
          sender() ! ErrorMessage(s"You are already in this chat as '${differentNickname.getOrElse(nickname)}'")
        } else {
          connected = connected + (nickname -> sender())
          sender() ! ChatLog(last10Log)
        }
      }
    case m@Message(chat, from, _) =>
      //TODO: only if sender is connected to this chat with this nickname
      //TODO: only if sender is connected to this chat with this nickname
      //TODO: append the message to the chatLog, store only last 10 messages
      //TODO: notify current connected users (except the sender) by a new message
      testChatName(chat) {
        if (connected.contains(from)) {
          if (connected(from) == sender()) {
            last10Log = (last10Log :+ m).takeRight(10)
            (connected.values.toSet - sender()).foreach(_ ! m)
          } else {
            sender() ! ErrorMessage(s"$from connected as different actor")
          }
        } else {
          sender() ! ErrorMessage(s"$from isn't in this chat")
        }
      }
    case GetChatLog(chat) =>
      //TODO: only if sent to this chat
      //TODO: reply with the log
      testChatName(chat) {
        sender() ! ChatLog(last10Log)
      }
  }
}