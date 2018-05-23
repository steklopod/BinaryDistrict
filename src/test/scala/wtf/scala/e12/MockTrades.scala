package wtf.scala.e12

import org.joda.time.DateTime

/**
  * Mock trades generation for [[wtf.scala.e12.ScalaTest]] test suite.
  */
object MockTrades {

  val baseMockDate: DateTime = DateTime.parse("2018-01-01T13:00")

  val baseMockPrice = 100.0

  /**
    * Returns a list of mock trades. The first trade has a price of 100.0 and
    * is dated at 2018-01-01T13:00. Each consecutive trade has a price higher
    * increased by 5.0 and is dated 3 minutes later.
    *
    * @param count number of mock trades to generate
    * @return mock trades
    */
  def createMockTrades(count: Int): List[Trade] = (0 until count).map { i =>
    Trade(
      price = baseMockPrice + 5 * i,
      time  = baseMockDate.plusMinutes(3 * i).getMillis
    )
  }.toList

}
