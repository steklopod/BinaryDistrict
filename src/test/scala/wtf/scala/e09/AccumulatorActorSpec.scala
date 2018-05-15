package wtf.scala.e09

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike, Matchers}
import wtf.scala.e09.AccumulatorActor._

class AccumulatorActorSpec extends TestKit(ActorSystem("AccumulatorActorSpec")) with ImplicitSender
  with FunSuiteLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  test("accumulator should set the value") {
    val accumulator = system.actorOf(AccumulatorActor.props)
    accumulator ! Set(4)
    accumulator ! Get
    expectMsg(4)
  }

  test("accumulator should add the value") {
    val accumulator = system.actorOf(AccumulatorActor.props)
    accumulator ! Add(4)
    accumulator ! Get
    expectMsg(4)
  }

  test("empty accumulator should return 0") {
    val accumulator = system.actorOf(AccumulatorActor.props)
    accumulator ! Get
    expectMsg(0)
  }
}