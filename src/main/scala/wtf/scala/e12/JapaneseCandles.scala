package wtf.scala.e12

/**
  * 'Japanese candlestick' is a way to statistically describe a numeric value change
  * over time.
  * <p>
  * It encompasses Open (starting), High (maximum), Low (minimum) and Close (ending)
  * values of a value array given (thus alternatively called OHLCs); as well as its
  * position in time, usually represented by a period start.
  * <p>
  * E.g., two trades with prices of $10 and $15 that happened on 13:07 and 13:12
  * respectively would result in an OHLC of [10,15,10,15,"13:00"] in case of a 15
  * minute-wide interval.
  * <p>
  * [[https://en.wikipedia.org/wiki/Candlestick_chart]]
  */
object JapaneseCandles {

  /**
    * Slices the timed values into candles of the interval given.
    *
    * @param timedValues - a sequence of timestamped numeric values
    * @param intervalMillis - a period in millisecond, defining the candles' "width"
    * @return a sequence of candle charts based on timed values provided
    */
  /*
    FIXME: this code contains errors intentionally for the sake of unit tests coverage study.
    HINT: there's something fishy about grouping by timestamp, and the close/low calculation,
    and the order of the candles returned.
  */
  def buildCandles[T](timedValues: Iterable[T], intervalMillis: Long)(implicit convert: T => TimedValue): List[Candle] =
    timedValues.groupBy { value =>
      value.timestamp % intervalMillis
    }.map { case (periodStartTimestamp, periodTimedValues) =>
      Candle(
        periodStart = periodStartTimestamp,
        open  = periodTimedValues.head.value,
        high  = periodTimedValues.maxBy(_.value).value,
        low   = periodTimedValues.maxBy(_.value).value,
        close = periodTimedValues.head.value
      )
    }.toList

}

trait TimedValue {
  def value: Double
  def timestamp: Long
}

case class Candle(
    periodStart: Long,
    open: Double,
    high: Double,
    low: Double,
    close: Double
)
