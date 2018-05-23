package wtf.scala.e13

import org.scalatest.Matchers
import scalikejdbc.config._
import scalikejdbc.scalatest.AutoRollback
import wtf.scala.e12.{MockTrades, Trade}

/**
  * Test suite for [[wtf.scala.e13.ScalikeJDBCTrades]].
  *
  * At the beginning of each test a 'trades' tables is supposed to be filled only
  * with 9 [[wtf.scala.e12.MockTrades]]-generated 3m-interval-separated trades.
  */
class ScalikeJDBCTradesSpec extends org.scalatest.fixture.FlatSpec with AutoRollback with Matchers {
  import scalikejdbc._

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:test", "test", "test")

  val initialTradesCount = 9
  val mockTrades: List[Trade] = MockTrades.createMockTrades(initialTradesCount)

  override def fixture(implicit session: DBSession) {
    ScalikeJDBCTrades.Trades.recreateTable
    mockTrades.foreach { trade =>
      ScalikeJDBCTrades.Trades.addTrade(trade)
    }
  }

  behavior of "ScalikeJDBCTrades.Trades"

  it should "create trades records" in { implicit session =>
    sql"select count(*) from trades".map(_.int(1)).single().apply() should equal(Some(initialTradesCount))
  }

  it should "implement apply() to map result set" in { implicit session =>
    sql"select * from trades".map(ScalikeJDBCTrades.Trades(_)).list.apply() should equal(mockTrades)
  }

  it should "find trades by timestamp" in { implicit session =>
    val tradesSubList = mockTrades.slice(1, 4)
    val startTimestamp = tradesSubList.head.time
    val endTimestamp = tradesSubList.last.time
    ScalikeJDBCTrades.Trades.findTrades(startTimestamp, endTimestamp) should equal(tradesSubList)
  }

}
