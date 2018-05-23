package wtf.scala.e12

import akka.actor.Actor

import scala.util.Random

case object UnknownStock
case object AnyStock
case class StockPrice(symbol: String, price: Double)

/**
  * This actor accepts a list of currencies, and must reply with a [[wtf.scala.e12.StockPrice]]
  * upon message receive. The messages accepted are either:
  *
  * - A currency name, to which a StockPrice of the same currency must be replied. If
  * a currency requested is not within the supplied currencies list, a [[wtf.scala.e12.UnknownStock]]
  * must be replied to the sender
  *
  * - [[wtf.scala.e12.AnyStock]], to which an actor can reply with a StockPrice, in such
  * a manner that each subsequent AnyStock message will result in a StockPrice of the
  * next currency from currencies list
  */
class CryptoPriceBot(val currencies: List[String]) extends Actor {

  require(currencies.size > 1, "Currencies count must be at least 2")

  private def generateStockPrice(currency: String)
    = StockPrice(currency, new Random().nextDouble())

  // FIXME: This code doesn't behave as it should by the spec intentionally. It's up to you
  // FIXME: to cover it with unit tests according to the CryptoPriceBot scala-doc and fix.
  override def receive: Receive = {
    case currency: String => sender() ! generateStockPrice(currencies.head)
    case AnyStock => sender() ! generateStockPrice(currencies.head)
  }

}
