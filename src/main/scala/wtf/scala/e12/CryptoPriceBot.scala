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

  private val currencyIterator: Iterator[String] = Iterator.continually(currencies).flatten

  private def generateStockPrice(currency: String)
    = StockPrice(currency, new Random().nextDouble())

  override def receive: Receive = {
    case currency: String =>
      if (currencies.contains(currency)) {
        sender() ! generateStockPrice(currencies.head)
      } else {
        sender() ! UnknownStock
      }

    case AnyStock => sender() ! generateStockPrice(currencyIterator.next())
  }

}
