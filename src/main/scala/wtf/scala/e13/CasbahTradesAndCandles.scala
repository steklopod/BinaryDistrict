package wtf.scala.e13

import com.mongodb.DBObject
import com.mongodb.casbah.MongoCollection
import wtf.scala.e12.{Candle, Trade}

/**
  * Now let's revisit the Japanese candles building, but this time using the MongoDB
  * awesome aggregation capabilities.
  *
  * We want to store trades as this kind of documents in MongoDB:
  *
  * <code>
  *   {
  *     "timestamp": 1560000000,
  *     "price": 11.200
  *   }
  * </code>
  *
  * To do so, you need to come up with some boilerplate code to manage the trading data.
  *
  * Once you're done with this, we'll practice MongoDB aggregation by building the Japanese
  * candles on the trading data using. Don't cheat and use any of the scala methods for
  * building a candle, your solution must be a pure MongoDB aggregation query.
  *
  * @see [[wtf.scala.e12.JapaneseCandles]]
  */
object CasbahTradesAndCandles {

  object TradeMapper {
    /**
      * Maps a Mongo DB object to a [[Trade]] instance
      */
    def apply(obj: DBObject): Trade = ???
  }

  def dropTradesCollection(implicit tradesCollection: MongoCollection): Unit = {
    tradesCollection.dropCollection()
  }

  def insertTrades(trades: List[Trade])(implicit tradesCollection: MongoCollection): Unit = {
    ???
  }

  /**
    * Hints:
    *  - use match aggregation to filter by date
    *  - use (timestamp - mod(timestamp, interval)) value as a grouping _id
    *  - use aggregation functions to build OHLC values
    */
  def aggregateCandles(startTimestamp: Long, endTimestamp: Long, interval: Long)
    (implicit tradesCollection: MongoCollection): List[Candle] = {
      ???
  }

}
