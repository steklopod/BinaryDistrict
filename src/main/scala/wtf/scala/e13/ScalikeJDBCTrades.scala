package wtf.scala.e13

import scalikejdbc._
import wtf.scala.e12.Trade

/**
  * For this exercise let's revisit the Trades and assume we want to store trading
  * data in a database table, and be able to retrieve it by the time range.
  *
  * @see [[wtf.scala.e12.Trade]]
  */
object ScalikeJDBCTrades {

  object Trades extends SQLSyntaxSupport[Trade] {
    override val tableName = "trades"
    override val columnNames = Seq("timestamp", "price")

    /**
      * Get a [[wtf.scala.e12.Trade]] from a result set
      */
    def apply(rs: WrappedResultSet): Trade = ???

    /**
      * Drop the table 'trades' if it exists and then recreate it.
      */
    def recreateTable(implicit session: DBSession): Unit = {
      ???
    }

    /**
      * Insert a trade into the 'trades' table.
      */
    def addTrade(trade: Trade)(implicit session: DBSession): Unit = {
      ???
    }

    /**
      * Find trades within a time range.
      *
      * Hint: make use of [[wtf.scala.e13.ScalikeJDBCTrades.Trades#apply]] here.
      */
    def findTrades(startTimestamp: Long, endTimestamp: Long)(implicit session: DBSession): List[Trade] = {
      ???
    }

  }

}
