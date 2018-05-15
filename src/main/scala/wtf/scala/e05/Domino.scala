package wtf.scala.e05

/**
  * Domino tile
  * @param head
  * @param tail
  */
case class Tile(head: Int, tail: Int) {
  assert(head >= 0 && head <= 6 && tail >= 0 && tail <= 6)

  def isDouble = head == tail
}

/**
  * For stock of domino tiles (no duplicate tiles) check
  * 1) Can the tiles in stock can be somehow connected all together
  * 2) Can the tiles be connected in one line
  * 3) Can the tiles be connected in a circle
  *
  * Stock of domino tiles
  * @param tiles - domino tiles
  */
case class Stock(tiles: List[Tile]) {

  /**
    * Returns values for all double-tiles
    */
  def getDoubles: List[Int] = {
    ???
  }

  /**
    * Returns map with number of occurrences of every score on of tiles
    */
  def countNumbers: Map[Int, Int] = {
    ???
  }

  /**
    * Counts the maximum number of endings the connected domino stock can have
    */
  def getMaxEndingsNumber: Int = {
    ???
  }

  /**
    * Checks if there is a double tile in a stock which has no tiles which can be connected to it
    */
  def hasLonelyDouble: Boolean = {
    ???
  }

  /**
    * Checks if the tiles in a stock can be somehow connected
    */
  def isConnectable: Boolean = {
    ???
  }

  /**
    * Checks if the tiles in a stock can be connected into a line
    */
  def isLineConnectable: Boolean = {
    ???
  }

  /**
    * Checks can the tiles in a stock be connected into the circle
    */
  def isCircleConnectable: Boolean = {
    ???
  }

}

object Stock {

  val MinTiles = 2
  val MaxTiles = 28

  def apply(tiles: Tile*): Stock = Stock(List(tiles: _*))

  val Tiles: List[Tile] = (for {
    head <- Range(0, 7)
    tail <- Range(0, head + 1)
  } yield Tile(head, tail)).toList

  val FullStock = Stock(Tiles)

  /**
    * Generate random stock
    * @param tileNumber - number of tiles in the generated stock
    * @return
    */
  def generate(tileNumber: Int): Stock = {
    ???
  }
}




