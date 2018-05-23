package wtf.scala.e12

import org.joda.time.Minutes
import org.scalatest._
import wtf.scala.e12.MockTrades.baseMockDate

/**
  * ScalaTest-backed tests.
  * <p>
  * This test suite is intended to test the japanese candlestick charts generation for the
  * trading data using [[wtf.scala.e12.JapaneseCandles#buildCandles]].
  * <p>
  * A helper-class [[wtf.scala.e12.MockTrades]] is provided to generate mock trades:
  *
  * {{{
  *   val mockTrades = MockTrades.createMockTrades(6)
  *   val fifteenMinMillis = Minutes.minutes(15).toStandardDuration.getMillis
  *   val candles = JapaneseCandles.buildCandles(mockTrades, fifteenMinMillis)
  * }}}
  *
  * @see [[wtf.scala.e12.JapaneseCandles]]
  * @see [[wtf.scala.e12.Trade]]
  */
class ScalaTest extends FlatSpec with Matchers {

  val fifteenMinMillis = Minutes.minutes(15).toStandardDuration.getMillis

  "JapaneseCandles" should "create a candle for each interval if at least one trade within that interval exists" in {
    val mockTrades = MockTrades.createMockTrades(6)
    val fifteenMinMillis = Minutes.minutes(15).toStandardDuration.getMillis
    val candles = JapaneseCandles.buildCandles(mockTrades, fifteenMinMillis)

    val candlesStartTimes = candles.map(_.periodStart)

    candlesStartTimes.size should equal(2)
    candlesStartTimes.distinct.sorted shouldEqual candlesStartTimes

    mockTrades.map(_.timestamp).map { ts =>
      ts - (ts % fifteenMinMillis)
    }.distinct.forall(candlesStartTimes.contains(_)) should equal(true)
  }

  it should "represent the intervals, sorted by interval start" in {
    val candlesBatches = (0 until 32).map { i =>
      val mockTrades = MockTrades.createMockTrades(6 + i)
      JapaneseCandles.buildCandles(mockTrades, fifteenMinMillis)
    }

    candlesBatches.foreach { candles =>
      val candlesStartTimes = candles.map(_.periodStart)
      candlesStartTimes.sorted shouldEqual candlesStartTimes
    }
  }

  it should "represent the time intervals correctly, excluding the intervals' ends, e.g., [13:00),[13:15),[13:30)..." in {
    val mockTrades = List(
      Trade(
        price = 5,
        time = baseMockDate.plusMinutes(0).getMillis
      ),
      Trade(
        price = 5,
        time = baseMockDate.plusMinutes(5).getMillis
      ),
      Trade(
        price = 5,
        time = baseMockDate.plusMinutes(10).getMillis
      ),
      Trade(
        price = 100,
        time = baseMockDate.plusMinutes(15).getMillis
      )
    )

    val candles = JapaneseCandles.buildCandles(mockTrades, fifteenMinMillis)
    candles.size shouldEqual 2

    val firstCandle = candles.head
    firstCandle.close shouldEqual 5
    firstCandle.high shouldEqual 5
  }

  it should "aggregate the values (open, close, high, low) correctly" in {
    val mockTrades = MockTrades.createMockTrades(10)
    val fifteenMinMillis = Minutes.minutes(15).toStandardDuration.getMillis
    val candles = JapaneseCandles.buildCandles(mockTrades, fifteenMinMillis)

    candles.size should equal(2)

    val firstCandle = candles.head
    firstCandle.open shouldEqual 100
    firstCandle.high shouldEqual 120
    firstCandle.low shouldEqual 100
    firstCandle.close shouldEqual 120

    val secondCandle = candles(1)
    secondCandle.open shouldEqual 125
    secondCandle.high shouldEqual 145
    secondCandle.low shouldEqual 125
    secondCandle.close shouldEqual 145
  }

}
