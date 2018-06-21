package wtf.scala.e13

import com.github.fakemongo.Fongo
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import org.scalatest.{Matchers, Outcome}
import wtf.scala.e12.{Candle, MockTrades, Trade}

/**
  * Test suite for [[wtf.scala.e13.CasbahTradesAndCandles]]
  *
  * At the beginning of each test a 'trades' collection is supposed to be filled only
  * with 9 [[wtf.scala.e12.MockTrades]]-generated 3m-interval-separated trades.
  */
class CasbahCandlesSpec extends org.scalatest.fixture.FlatSpec with Matchers {

  type FixtureParam = MongoCollection

  private val fongo = new Fongo("CasbahCandlesSpec")

  val Host = "localhost:27017"
  val DBName = "casbah_test"
  val TradesCollName = "trades"

  val initialTradesCount = 9
  val mockTrades: List[Trade] = MockTrades.createMockTrades(initialTradesCount)

  val MillisIn15Minutes: Long = 1000 * 60 * 15

  override protected def withFixture(test: OneArgTest): Outcome = {
    implicit val tradesCollection: MongoCollection = new MongoCollection(fongo.getDB(DBName).getCollection(TradesCollName))
    try {
      tradesCollection.dropCollection()
      CasbahTradesAndCandles.insertTrades(mockTrades)
      test(tradesCollection)
      withFixture(test.toNoArgTest(tradesCollection))
    } finally {
      tradesCollection.dropCollection()
    }
  }

  behavior of "TradeMapper"

  it should "map the mongodb objects to trades correctly" in { _ =>
    CasbahTradesAndCandles.TradeMapper(MongoDBObject(
      "timestamp" -> 1560000000l,
      "price"     -> 11.200
    )) should equal(Trade(11.200, 1560000000l))
  }

  behavior of "CasbahTradesAndCandles"

  it should "insert the trades correctly" in { implicit tradesCollection =>
    tradesCollection.toList.map(CasbahTradesAndCandles.TradeMapper(_)) should equal (mockTrades)
  }

  // todo it works only with a real mongodb
  ignore should "return 2 candles (based on 9 3m-interval trades)" in { implicit tradesCollection =>
    val startTs = mockTrades.head.timestamp
    val endTs   = mockTrades.last.timestamp
    val candles = CasbahTradesAndCandles.aggregateCandles(startTs, endTs, MillisIn15Minutes)
    candles.size should equal(2)
  }

  ignore should "return candles sorted by period start" in { implicit tradesCollection =>
    val startTs = mockTrades.head.timestamp
    val endTs   = mockTrades.last.timestamp
    val candles = CasbahTradesAndCandles.aggregateCandles(startTs, endTs, MillisIn15Minutes)
    candles.map(_.periodStart) shouldBe sorted
  }

  ignore should "return candles with period start timestamps dividable by 15m in millis, " +
      "and within the timestamps of the trades" in { implicit tradesCollection =>
    val startTs = mockTrades.head.timestamp
    val endTs   = mockTrades.last.timestamp
    val candles = CasbahTradesAndCandles.aggregateCandles(startTs, endTs, MillisIn15Minutes)

    val candlesPeriodStarts = candles.map(_.periodStart)
    candlesPeriodStarts.forall(_ % MillisIn15Minutes == 0) shouldBe true
    val tradesTimeRange = startTs to endTs
    candlesPeriodStarts.forall(tradesTimeRange.contains(_)) shouldBe true
  }

  ignore should "return the candles with correct OHLC values" in { implicit tradesCollection =>
    val startTs = mockTrades.head.timestamp
    val endTs   = mockTrades.last.timestamp

    println(mockTrades.map(_.price))

    def assertTradesGroupOHLCMatchesCandle(trades: List[Trade], candle: Candle): Unit = {
      val prices = trades.map(_.price)
      prices.max should equal(candle.high)
      prices.min should equal(candle.low)
      prices.head should equal(candle.open)
      prices.last should equal(candle.close)
    }

    val tradeGroupsBy15MinutePeriodStart = mockTrades.groupBy { trade =>
      trade.time - (trade.time % MillisIn15Minutes)
    }.toList.sortBy(_._1).map(_._2)

    tradeGroupsBy15MinutePeriodStart match {
      case List(firstTradesGroup, secondTradesGroup) =>
        CasbahTradesAndCandles.aggregateCandles(startTs, endTs, MillisIn15Minutes) match {
          case List(firstCandle, secondCandle) =>
            assertTradesGroupOHLCMatchesCandle(firstTradesGroup, firstCandle)
            assertTradesGroupOHLCMatchesCandle(secondTradesGroup, secondCandle)
        }
    }
  }

}
