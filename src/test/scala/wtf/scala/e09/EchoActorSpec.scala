package wtf.scala.e09

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike, Matchers}

class EchoActorSpec extends TestKit(ActorSystem("AccumulatorActorSpec")) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  test("send back messages unchanged") {
    val echo = system.actorOf(EchoActor.props)
    echo ! "hello world"
    expectMsg("hello world")
  }
}