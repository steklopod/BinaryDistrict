package wtf.scala.e10

import wtf.scala.e10.tcpchat.ChatManagerActor

class ChatManagerActorSpec extends ChatLikeActorTest {
  override protected def beforeEach(): Unit = {
    testChat = system.actorOf(ChatManagerActor.props, "test")
  }
}