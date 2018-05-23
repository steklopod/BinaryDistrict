package wtf.scala.e12

import org.scalatest._

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

  "JapaneseCandles" should "create a candle for each interval if at least one trade within that interval exists" in {
    ???
  }

  it should "represent the intervals, sorted by interval start" in {
    ???
  }

  it should "represent the time intervals correctly, excluding the intervals' ends, e.g., [13:00),[13:15),[13:30)..." in {
    ???
  }

  it should "aggregate the values (open, close, high, low) correctly" in {
    ???
  }

}
