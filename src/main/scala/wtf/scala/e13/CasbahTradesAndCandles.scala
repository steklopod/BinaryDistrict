package wtf.scala.e13

import com.mongodb.DBObject
import com.mongodb.casbah.Imports.MongoDBObject
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBList
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
    def apply(obj: DBObject): Trade = Trade(
      time  = obj.get("timestamp").asInstanceOf[Long],
      price = obj.get("price").asInstanceOf[Double]
    )
  }

  def dropTradesCollection(implicit tradesCollection: MongoCollection): Unit = {
    tradesCollection.dropCollection()
  }

  def insertTrades(trades: List[Trade])(implicit tradesCollection: MongoCollection): Unit = {
    tradesCollection.insert(trades.map { trade =>
      MongoDBObject(
        "timestamp" -> trade.time,
        "price"     -> trade.price
      )
    }:_*)
  }

  /**
    * Hints:
    *  - use match aggregation to filter by date
    *  - use (timestamp - mod(timestamp, interval)) value as a grouping _id
    *  - use aggregation functions to build OHLC values
    */
  def aggregateCandles(startTimestamp: Long, endTimestamp: Long, interval: Long)
    (implicit tradesCollection: MongoCollection): List[Candle] = {
    tradesCollection.aggregate(List(
      MongoDBObject("$match" -> MongoDBObject("timestamp" -> MongoDBObject(
        "$gte" -> startTimestamp,
        "$lte" -> endTimestamp
      ))),
      MongoDBObject("$group" -> MongoDBObject(
        "_id" -> MongoDBObject(
          "intervalStart" -> MongoDBObject(
            "$subtract" -> MongoDBList(
              "$timestamp",
              MongoDBObject(
                "$mod" -> MongoDBList(
                  "$timestamp",
                  interval
                )
              )
            )
          )
        ),
        "open"  -> MongoDBObject("$first" -> "$price"),
        "high"  -> MongoDBObject("$max"   -> "$price"),
        "close" -> MongoDBObject("$last"  -> "$price"),
        "low"   -> MongoDBObject("$min"   -> "$price")))
    )).results.map { result =>
      Candle(
        periodStart = result.get("_id").asInstanceOf[DBObject].get("intervalStart").asInstanceOf[Long],
        open  = result.get("open").asInstanceOf[Double],
        high  = result.get("high").asInstanceOf[Double],
        low   = result.get("low").asInstanceOf[Double],
        close = result.get("close").asInstanceOf[Double]
      )
    }
  }.toList.sortBy(_.periodStart)

}
