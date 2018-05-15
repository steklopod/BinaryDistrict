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

  def receive: PartialFunction[Any, Unit] = {
    case LeaveChat(chat, nickname) =>
      //TODO: only if sent to this chat
      //TODO: only if sender is connected to this chat with that username else answer with ErrorMessage
      ???
    case EnterChat(chat, nickname) =>
      //TODO: only if sent to this chat
      //TODO: only if sender is not connected to this chat else answer with ErrorMessage
      ???
    case m@Message(chat, from, _) =>
      //TODO: only if sender is connected to this chat with this nickname
      //TODO: only if sender is connected to this chat with this nickname
      //TODO: append the message to the chatLog, store only last 10 messages
      //TODO: notify current connected users (except the sender) by a new message
      ???
    case GetChatLog(chat) =>
      //TODO: only if sent to this chat
      //TODO: reply with the log
      ???
  }
}