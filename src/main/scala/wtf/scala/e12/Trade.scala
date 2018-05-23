package wtf.scala.e12

object Trade {
  implicit def trade2HasDoubleValue(trade: Trade) = new TimedValue {
    override def value: Double = trade.price
    override def timestamp: Long = trade.time
  }
}

case class Trade(price: Double, time: Long)
