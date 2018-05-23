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
      ???
    }
    "send back UnknownStock on an unknown currency request" in {
      ???
    }
    "send back any stock price on AnyStock message" in {
      ???
    }
    "cycle through its configured currencies list on subsequent AnyStock messages" in {
      ???
    }
  }
}
