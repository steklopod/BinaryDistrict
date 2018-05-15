package wtf.scala.e10

import akka.testkit.TestProbe
import wtf.scala.e10.tcpchat.ChatActor
import wtf.scala.e10.tcpchat.ChatManagerActor._

class ChatActorSpec extends ChatLikeActorTest {
  override protected def beforeEach(): Unit = {
    testChat = system.actorOf(ChatActor.props, "test")
  }

  test("chat actor should send an error message on enter message with wrong chat name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Seq.empty))
    val m = Message("test1", "trololo", "Hi!")
    testChat ! m
    expectMsg(ErrorMessage("Wrong chat name"))
  }
  test("chat actor should send an error message on leave chat with wrong chat name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    testChat ! LeaveChat("test1", "trololo")
    expectMsg(ErrorMessage("Wrong chat name"))
  }

  test("chat actor should send an error message on leave chat with wrong user name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    val secondUser = TestProbe("testUser")
    secondUser.send(testChat, EnterChat("test", "trololo1"))
    secondUser.expectMsg(ChatLog(Vector.empty))
    testChat ! LeaveChat("test", "trololo1")
    expectMsg(ErrorMessage("trololo1 connected as different actor"))
  }

  test("chat actor should send an error message on message with wrong user name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    val secondUser = TestProbe("testUser")
    secondUser.send(testChat, EnterChat("test", "trololo1"))
    secondUser.expectMsg(ChatLog(Vector.empty))
    testChat ! Message("test", "trololo1", ":)")
    expectMsg(ErrorMessage("trololo1 connected as different actor"))
  }

  test("chat actor should send an error message on message with wrong chat name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    val m = Message("test1", "trololo", "Hi!")
    testChat ! m
    expectMsg(ErrorMessage("Wrong chat name"))
  }
  test("chat actor should send an error message on get log message with wrong chat name") {
    testChat ! EnterChat("test", "trololo")
    expectMsg(ChatLog(Vector.empty))
    testChat ! GetChatLog("test1")
    expectMsg(ErrorMessage("Wrong chat name"))
  }
}
