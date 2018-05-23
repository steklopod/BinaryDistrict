package wtf.scala.e12

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
  * A test suite to check whether or not [[wtf.scala.e12.CryptoPriceBot]] behaves
  * accordingly to its spec (see its scala-doc).
  *
  * Compete the tests and fix the CryptoPriceBot so it does.
  */
class AkkaTestKit extends TestKit(ActorSystem("CryptoBotSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  val mockCurrencies = List("BUTTC", "DOGE", "NOCOIN")
  val cryptoBotProps = Props(classOf[CryptoPriceBot], mockCurrencies)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A crypto-bot actor" must {
    "send back stock price of a currency requested" in {
      val cryptoBot = system.actorOf(cryptoBotProps)
      cryptoBot ! "BUTTC"
      expectMsgPF() {
        case StockPrice("BUTTC", _) => ;
        case _ => fail("StockPrice expected")
      }
    }

    "send back UnknownStock on an unknown currency request" in {
      val cryptoBot = system.actorOf(cryptoBotProps)
      cryptoBot ! "WEIRDCOIN"
      expectMsgPF() {
        case UnknownStock => ;
        case _ => fail("UnknownStock expected")
      }
    }

    "send back any stock price on AnyStock message" in {
      val cryptoBot = system.actorOf(cryptoBotProps)
      cryptoBot ! AnyStock
      expectMsgPF() {
        case StockPrice(_, _) => ;
        case _ => fail("StockPrice expected")
      }
    }

    "cycle through its configured currencies list on subsequent AnyStock messages" in {
      val cryptoBot = system.actorOf(cryptoBotProps)

      for (_ <- 0 until mockCurrencies.length * 2) {
        cryptoBot ! AnyStock
      }

      val messages = receiveN(mockCurrencies.length * 2)
      messages.length should equal(mockCurrencies.length * 2)
      messages.forall(_.isInstanceOf[StockPrice]) should equal(true)

      val receivedCurrencies = messages.map(_.asInstanceOf[StockPrice]).map(_.symbol)

      receivedCurrencies.toSet.subsetOf(mockCurrencies.toSet) should equal(true)
      mockCurrencies.toSet.subsetOf(receivedCurrencies.toSet) should equal(true)

      receivedCurrencies.sliding(2).map { window =>
        (window.head, window(1))
      }.forall { tuple =>
        tuple._1 != tuple._2
      } should equal(true)
    }
  }
}
