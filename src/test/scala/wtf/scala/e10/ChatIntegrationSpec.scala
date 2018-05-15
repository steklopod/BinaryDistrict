package wtf.scala.e10

import akka.actor.{ActorRef, ActorSystem}
import akka.io.Tcp.{Received, Write}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import akka.util.ByteString
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuiteLike, Matchers}
import wtf.scala.e10.tcpchat.ChatManagerActor.Message
import wtf.scala.e10.tcpchat.{ChatManagerActor, UserActor}

class ChatIntegrationSpec extends TestKit(ActorSystem("Chat")) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {

  protected var testUser: ActorRef = _
  protected var connection: TestProbe = _
  protected var chatManagerActor: ActorRef = _


  override protected def beforeEach(): Unit = {
    connection = TestProbe()
    chatManagerActor = system.actorOf(ChatManagerActor.props, "chat_manager")
    testUser = system.actorOf(UserActor.props(connection.ref, chatManagerActor), "test")
  }

  override protected def afterEach(): Unit = {
    system.stop(testUser)
    system.stop(chatManagerActor)
  }

  test("user actor should send welcome message on connect") {
    connection.expectMsg(Write(ByteString("Enter your nickname:\n")))
  }

  def goToAuthorizedState(testUser: ActorRef, connection: TestProbe=connection, name: String = "trololo"): Unit = {
    connection.expectMsg(Write(ByteString("Enter your nickname:\n")))
    testUser ! Received(ByteString(name))
    connection.expectMsg(Write(ByteString(s"Hi, ${name.trim}\n")))
  }

  test("user actor in anonymous state should goes to an authorized state after sending a non empty nickname") {
    goToAuthorizedState(testUser)
  }

  test("user actor in anonymous state should goes to an authorized state after sending a non empty nickname with the end line") {
    goToAuthorizedState(testUser, name = "trololo\n")
  }

  test("user actor in anonymous state should goes not to an authorized state after sending an empty nickname") {
    connection.expectMsg(Write(ByteString("Enter your nickname:\n")))
    testUser ! Received(ByteString(""))
    connection.expectNoMessage()
    testUser ! Received(ByteString("\n"))
    connection.expectNoMessage()
  }

  test("user actor should receive no log messages after connecting to a chat without history") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("join:test\n"))
    connection.expectNoMessage()
  }

  test("user actor should receive last 10 log messages after ask an exised chat with some history") {
    val secondUserConnection = TestProbe()
    val secondUser = system.actorOf(UserActor.props(secondUserConnection.ref, chatManagerActor), "test2")
    goToAuthorizedState(secondUser, secondUserConnection, "test2")

    secondUser ! Received(ByteString("join:test"))
    val messages = (0 until 15).map(i => Message("test", "test2", s"Hi!$i"))
    messages foreach (m => secondUser ! Received(ByteString(m.toMessageString)))

    Thread.sleep(1000)

    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("join:test"))
    connection.expectMsg(Write(ByteString(messages.takeRight(10).map(_.toString).mkString("\n"))))
  }

  test("user actor should receive an error message after try to connect again with the same nickname") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("join:test\n"))
    connection.expectNoMessage()
    testUser ! Received(ByteString("join:test\n"))
    connection.expectMsg(Write(ByteString("!You are already in this chat as 'trololo'\n")))
  }

  test("user actor should receive an error message after try to connect without chat name") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("join\n"))
    connection.expectMsg(Write(ByteString("!Invalid command\n")))
  }

  test("user actor should receive an error message after try to leave without chat name") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("leave\n"))
    connection.expectMsg(Write(ByteString("!Invalid command\n")))
  }

  test("user actor should receive an error message after try to send without chat name or message") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("send:as\n"))
    connection.expectMsg(Write(ByteString("!Invalid command\n")))
    testUser ! Received(ByteString("send\n"))
    connection.expectMsg(Write(ByteString("!Invalid command\n")))
  }

  test("user actor should receive an error message on leave chat with wrong chat name") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("leave:test\n"))
    connection.expectMsg(Write(ByteString("!trololo isn't in this chat\n")))
  }

  test("user actor should receive an error message on message with wrong chat name") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString(Message("test", "somenick", "Hi!").toMessageString))
    connection.expectMsg(Write(ByteString("!trololo isn't in this chat\n")))
  }
}
