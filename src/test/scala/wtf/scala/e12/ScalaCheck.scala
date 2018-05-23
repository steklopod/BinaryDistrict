package wtf.scala.e12

import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

/**
  * ScalaCheck-backed tests.
  *
  * In this example we're going to practice writing ScalaCheck generators,
  * test those generators with ScalaCheck checks, then do some real testing
  * by generating arbitrary trades within the same 15m intervals and checking
  * whether or not we build the Japanese Candles correctly.
  *
  * It's preferable to read the [[wtf.scala.e12.JapaneseCandles]] docs and do
  * the ScalaTest-based tests first.
  *
  * @see [[wtf.scala.e12.JapaneseCandles]]
  * @see [[wtf.scala.e12.Trade]]
  */
class ScalaCheck extends FunSuite with Checkers {

  val startTimestamp: Long = System.currentTimeMillis()
  val fifteenMinutesMillis: Long = 1000 * 60 * 15
  def fifteenMinutesBase(timestamp: Long) = timestamp - (timestamp % fifteenMinutesMillis)

  /**
    * Generates a timestamp within 15m interval, i.e., any timestamp generated will
    * belong to [XXh:00m,XXh:15m), [XXh:15m,XXh:30m), [XXh:30m,XXh:45m) or [XXh:45m,XX+1h:00m)
    */
  val timestampWithin15MinutesGen: Gen[Long] = for {
    fifteenMinutesBase <- fifteenMinutesBase(startTimestamp)
    intervalTimestamp  <- Gen.choose(fifteenMinutesBase, fifteenMinutesBase + fifteenMinutesMillis - 1)
  } yield intervalTimestamp

  /**
    * Let's check that it's actually true about 'timestampWithin15MinutesGen'.
    * Complete this test to check whether the two timestamps are in the same 15m interval.
    *
    * You might want to use fifteenMinutesBase() here.
    */
  test("Check that any timestamp generated with timestampWithin15MinutesGen lies within the single 15m period") {
    check(
      forAll(timestampWithin15MinutesGen, timestampWithin15MinutesGen) { (timestamp1: Long, timestamp2: Long) =>
        ???
      }
    )
  }

  /**
    * Ok, now let's generate some Trades within one 15-minute interval.
    *
    * Replace the ??? with the actual timestamp and price generators.
    * Generate the prices within the [10.0, 20.0] range.
    */
  // TODO: ignore the const, just can't put a '???' invocation there as a placeholder
  val within15MinuteTradeGen: Gen[Trade] = for {
    timestamp <- Gen.const(???)
    price     <- Gen.const(???)
  } yield Trade(price, timestamp)

  implicit val arb15MinTrade: Arbitrary[Trade] = Arbitrary { within15MinuteTradeGen }

  /**
    * Now let's complete some tests to check if our trades generator is doing fine.
    */
  test("Check that all trades generated with arb15MinTrade are within the same 15m interval") {
    check(forAll((trade1: Trade, trade2: Trade) =>
      ???
    ))
  }

  test("Check that trades' prices are within the [10.0, 20.0] range") {
    check(forAll((trade: Trade) =>
      ???
    ))
  }

  /**
    * Now let's do a trades list generator.
    */
  implicit val arb15MinTrades: Arbitrary[List[Trade]] = Arbitrary {
    ???
  }

  /**
    * Finally, we can do some real testing. Let's test whether or not
    * we generate the Japanese Candles from trades lists correctly.
    */
  test("Check that we generate only generate a single candle for trades within one 15m interval") {
    check(forAll((trades: List[Trade]) =>
      ???
    ))
  }

  test("Check that for any trades given, the OHLC values are calculated correctly") {
    ???
  }

}
