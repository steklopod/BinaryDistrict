package wtf.scala.e05

import scala.util.Random

case class Connection(first: Tile, second: Tile, by: Int)

/**
  * Domino tile
  *
  * @param head
  * @param tail
  */
case class Tile(head: Int, tail: Int) {
  assert(head >= 0 && head <= 6 && tail >= 0 && tail <= 6)

  def isDouble: Boolean = head == tail

  def asTilesPartsSeq: Seq[Int] = Seq(head, tail)

  def asFreeTilesPartsSeq: Seq[Int] = if (isDouble) {
    Seq.fill(4)(head)
  } else {
    Seq(head, tail)
  }
}

object ConnectedStock {
  def fromTile(tile: Tile): ConnectedStock = ConnectedStock(Set(tile), tile.asFreeTilesPartsSeq.toSet[Int].map(part => part -> {
    if (tile.isDouble) {
      Seq.fill(4)(tile)
    } else {
      Seq(tile)
    }
  }).toMap.withDefaultValue(Seq.empty))
}

case class ConnectedStock(connectedTiles: Set[Tile] = Set.empty, freeTileParts: Map[Int, Seq[Tile]] = Map.empty.withDefaultValue(Seq.empty)) {

  def addConnection(connection: Connection): ConnectedStock = {
    val freeTilePartsAfterOldPartsRemove =
      freeTileParts
        // just removing a tile from the free tile parts list
        .updated(connection.by, freeTileParts(connection.by).patch(freeTileParts(connection.by).indexOf(connection.first), Nil, 1))
        .updated(connection.by, freeTileParts(connection.by).patch(freeTileParts(connection.by).indexOf(connection.second), Nil, 1))

    val newTiles = Seq(connection.first, connection.second).filterNot(connectedTiles.contains)
    val newTilePartsAfterNewPartsInsert = newTiles.foldLeft(freeTilePartsAfterOldPartsRemove) {
      case (parts, tile) =>
        val freePartsLeft = tile.asFreeTilesPartsSeq.patch(tile.asFreeTilesPartsSeq.indexOf(connection.by), Nil, 1)
        freePartsLeft.foldLeft(parts) {
          case (parts, freePart) =>
            parts.updated(freePart, parts(freePart) :+ tile)
        }
    }

    ConnectedStock(connectedTiles ++ newTiles, newTilePartsAfterNewPartsInsert)
  }

  def possibleConnectionsWith(tile: Tile): Iterator[Connection] = {
    for {
      tilePart <- tile.asFreeTilesPartsSeq.toIterator
      canBeConnected <- freeTileParts(tilePart)
    } yield Connection(tile, canBeConnected, tilePart)
  }

  def possibleStocksWithTile(tile: Tile): Iterator[ConnectedStock] = {
    possibleConnectionsWith(tile).map(addConnection)
  }

  def possibleStocksWithTiles(tiles: List[Tile]): Iterator[ConnectedStock] = {
    tiles.toIterator
      .map(tile => tile -> possibleStocksWithTile(tile)).flatMap {
      case (tile, stocks) =>
        val tilesLeft = tiles.patch(tiles.indexOf(tile), Nil, 1)
        val stocksStream = stocks.toStream
        stocksStream ++ stocksStream.flatMap(_.possibleStocksWithTiles(tilesLeft))
    }
  }

  def getMaxEndingsNumber: Int = freeTileParts.map(_._2.size).sum
}

/**
  * For stock of domino tiles (no duplicate tiles) check
  * 1) Can the tiles in stock can be somehow connected all together
  * 2) Can the tiles be connected in one line
  * 3) Can the tiles be connected in a circle
  *
  * Stock of domino tiles
  *
  * @param tiles - domino tiles
  */
case class Stock(tiles: List[Tile]) {
  def possibleStocks: Iterator[ConnectedStock] = {
    tiles.toIterator.flatMap(tile => {
      val other = tiles.patch(tiles.indexOf(tile), Nil, 1)
      ConnectedStock.fromTile(tile).possibleStocksWithTiles(other)
    })
  }

  /**
    * Returns values for all double-tiles
    */
  def getDoubles: List[Int] = {
    tiles.filter(_.isDouble).map(_.head)
  }

  /**
    * Returns map with number of occurrences of every score on of tiles
    */
  def countNumbers: Map[Int, Int] = {
    tiles.flatMap(_.asTilesPartsSeq).groupBy(identity).mapValues(_.size).withDefaultValue(0)
  }

  /**
    * Counts the maximum number of endings the connected domino stock can have
    */
  def getMaxEndingsNumber: Int = {
    val maxEndings = possibleStocks
    if (possibleStocks.nonEmpty) {
      maxEndings.grouped(1000000).map(_.par.maxBy(_.getMaxEndingsNumber).getMaxEndingsNumber).max
    } else {
      0
    }
  }

  /**
    * Checks if there is a double tile in a stock which has no tiles which can be connected to it
    */
  def hasLonelyDouble: Boolean = {
    val doubles = tiles.filter(_.isDouble)
    doubles.exists(d => {
      val other = tiles.patch(tiles.indexOf(d), Nil, 1)
      ConnectedStock.fromTile(d).possibleStocksWithTiles(other).isEmpty
    })
  }

  /**
    * Checks if the tiles in a stock can be somehow connected
    */
  def isConnectable: Boolean = {
    val total = tiles.size
    possibleStocks.exists(_.connectedTiles.size == total)
  }

  /**
    * Checks if the tiles in a stock can be connected into a line
    */
  def isLineConnectable: Boolean = {
    val total = tiles.size
    isConnectable && possibleStocks.exists(stock => stock.connectedTiles.size == total &&
      stock.freeTileParts.flatMap(_._2).toSet.size == 2)
  }

  /**
    * Checks can the tiles in a stock be connected into the circle
    */
  def isCircleConnectable: Boolean = {
    val total = tiles.size
    possibleStocks.exists(stock => {
      val unconnectedTiles = stock.freeTileParts.flatMap(_._2).toSet.toSeq

      def unconnectedTilesAreInterconnectable = unconnectedTiles.forall(t => {
        val tileFreeTilePart = stock.freeTileParts.find(_._2.contains(t)).get._1
        stock.freeTileParts(tileFreeTilePart).nonEmpty && stock.freeTileParts(tileFreeTilePart).exists(_ != t)
      })

      stock.connectedTiles.size == total &&
        unconnectedTiles.size == 2 &&
        unconnectedTilesAreInterconnectable
    })
  }

}

object Stock extends App {

  def apply(tiles: Tile*): Stock = Stock(List(tiles: _*))

  /**
    * Generate random stock
    *
    * @param tileNumber - number of tiles in the generated stock
    * @return
    */
  def generate(tileNumber: Int): Stock = {
    def randomTileNumber: Int = Random.nextInt(7)

    Stock(List.fill(tileNumber)(Tile(randomTileNumber, randomTileNumber)))
  }
}




