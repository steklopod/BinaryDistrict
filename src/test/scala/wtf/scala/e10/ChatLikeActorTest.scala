package wtf.scala.e10

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuiteLike, Matchers}
import wtf.scala.e10.tcpchat.ChatManagerActor._

abstract class ChatLikeActorTest extends TestKit(ActorSystem("Chat")) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  protected var testChat: ActorRef = _

  override protected def beforeEach()

  override protected def afterEach(): Unit = {
    system.stop(testChat)
    Thread.sleep(100)
  }

  test("chat actor should send empty log after connecting to a new chat") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
  }

  test("chat actor should send non empty log after connecting to an exised chat with some history") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Seq.empty))
    val m = Message("test", "trololo", "Hi!")
    testChat ! m
    testChat ! LeaveChat("test", "trololo")
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Seq(m)))
  }

  test("chat actor should send last 10 log messages after ask an exised chat with some history") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Seq.empty))
    val messages = (0 until 15).map(i => Message("test", "trololo", s"Hi!$i"))
    messages.foreach(testChat ! _)
    testChat ! GetChatLog("test")
    expectMsg(ChatLog(messages.takeRight(10)))
  }

  test("chat actor should send an error message after try to connect again with the same nickname") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    testChat ! EnterChat("test", "trololo")
    expectMsg(ErrorMessage("You are already in this chat as 'trololo'"))
  }

  test("chat actor should send an error message after try to send a message with wrong nickname") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    val m = Message("test", "trololo1", "Hi!")
    testChat ! m
    expectMsg(ErrorMessage("trololo1 isn't in this chat"))
  }

  test("chat actor should send an error message after try to connect again with a different nickname") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    testChat ! EnterChat("test", "trololo123")
    expectMsg(ErrorMessage("You are already in this chat as 'trololo'"))
  }

  test("chat actor should send an error message after try to leave not connected testChat") {
    testChat ! LeaveChat("test", "trololo")
    expectMsg(ErrorMessage("trololo isn't in this chat"))
  }
}
