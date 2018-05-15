package wtf.scala.e10

import akka.actor.{ActorRef, ActorSystem}
import akka.io.Tcp.{Received, Write}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import akka.util.ByteString
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuiteLike, Matchers}
import wtf.scala.e10.tcpchat.ChatManagerActor.{EnterChat, GetChatLog, LeaveChat, Message}
import wtf.scala.e10.tcpchat.UserActor

class UserActorSpec extends TestKit(ActorSystem("Chat")) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {

  protected var testUser: ActorRef = _
  protected var connection: TestProbe = TestProbe()
  protected var chatManagerActor: TestProbe = TestProbe()


  override protected def beforeEach(): Unit = {
    testUser = system.actorOf(UserActor.props(connection.ref, chatManagerActor.ref), "test")
  }

  override protected def afterEach(): Unit = {
    system.stop(testUser)
    Thread.sleep(100)
  }

  test("user actor should send welcome message on connect") {
    connection.expectMsg(Write(ByteString("Enter your nickname:\n")))
  }

  def goToAuthorizedState(testUser: ActorRef): Unit = {
    connection.expectMsg(Write (ByteString("Enter your nickname:\n")))
    testUser ! Received(ByteString("trololo"))
    connection.expectMsg(Write(ByteString("Hi, trololo\n")))
  }

  test("user actor in anonymous state should goes to an authorized state after sending a non empty nickname") {
    goToAuthorizedState(testUser)
  }

  test("user actor should send enter chat message when receive the join command") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("join:test"))
    chatManagerActor.expectMsg(EnterChat("test", "trololo"))
  }

  test("user actor should send leave chat message when receive the leave command") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("leave:test"))
    chatManagerActor.expectMsg(LeaveChat("test", "trololo"))
  }

  test("user actor should send 'message' chat message when receive the send command") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("send:test:msg"))
    chatManagerActor.expectMsg(Message("test", "trololo", "msg"))
  }

  test("user actor should send get log chat message when receive the log command") {
    goToAuthorizedState(testUser)
    testUser ! Received(ByteString("log:test"))
    chatManagerActor.expectMsg(GetChatLog("test"))
  }
}
