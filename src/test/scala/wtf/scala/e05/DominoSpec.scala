package wtf.scala.e05

import org.scalatest.{FunSuite, Matchers}

class DominoSpec extends FunSuite with Matchers {

  val NonConnectableStockWithDouble = Stock(Tile(1, 2), Tile(3, 4), Tile(3, 3))
  val NonConnectableStock = Stock(Tile(1, 2), Tile(2, 3), Tile(4, 4))

  val CrossStock = Stock(Tile(0, 0), Tile(0, 1), Tile(0, 2), Tile(0, 3), Tile(0, 4))
  val CrossAndHalfCrossStock = Stock(Tile(0, 0), Tile(0, 1), Tile(0, 2), Tile(0, 3), Tile(0, 4), Tile(4, 5), Tile(5, 5), Tile(1, 5), Tile(2, 5))
  val DoubleCrossStock = Stock(Tile(0, 0), Tile(0, 1), Tile(0, 2), Tile(0, 3), Tile(0, 4), Tile(4, 5), Tile(5, 5), Tile(1, 5), Tile(2, 5), Tile(3, 5))

  val LineStock = Stock(Tile(1, 2), Tile(2, 3), Tile(3, 5), Tile(5,2))
  val LineStockWithDouble = Stock(Tile(1, 2), Tile(2, 2), Tile(2, 3), Tile(3, 5), Tile(5,2))

  val CircleStock = Stock(Tile(1, 2), Tile(2, 4), Tile(4, 5), Tile(1, 5))
  val CircleStockWithDouble = Stock(Tile(1, 2), Tile(2, 2), Tile(2, 4), Tile(4, 5), Tile(1, 5))

  test("should find doubles in stock") {
      val doubles = NonConnectableStock.getDoubles
      doubles should contain only(4)
  }

  test("Should count number of included numbers") {
    val numbers = NonConnectableStock.countNumbers

    numbers.keys should contain only(1, 2, 3, 4)
    numbers(1) should equal(1)
    numbers(2) should equal(2)
    numbers(3) should equal(1)
    numbers(4) should equal(2)
  }

  test("Should check if stock has lonely double") {
    NonConnectableStock.hasLonelyDouble should be(true)
  }

  test("Should count maximum endings number") {
    CrossStock.getMaxEndingsNumber should equal(4)
    // it is quite difficult, you can uncomment it if you are ready
    CrossAndHalfCrossStock.getMaxEndingsNumber should equal(6)
    DoubleCrossStock.getMaxEndingsNumber should equal(6)
    LineStock.getMaxEndingsNumber should equal(2)
  }

  test("Should check if stock can be connected") {
    NonConnectableStockWithDouble.isConnectable should be(false)

    CrossStock.isConnectable should be(true)
    CrossAndHalfCrossStock.isConnectable should be(true)
    DoubleCrossStock.isConnectable should be(true)

    LineStock.isConnectable should be(true)
    LineStockWithDouble.isConnectable should be(true)

    CircleStock.isConnectable should be(true)
    CircleStockWithDouble.isConnectable should be(true)
  }

  test("should check if stock can be line connected") {
    NonConnectableStockWithDouble.isLineConnectable should be(false)

    CrossStock.isLineConnectable should be(false)

    LineStock.isLineConnectable should be(true)
    LineStockWithDouble.isLineConnectable should be(true)

    CircleStock.isLineConnectable should be(true)
    CircleStockWithDouble.isLineConnectable should be(true)
  }

  test("Should check if stock can be circle connected") {
    NonConnectableStockWithDouble.isCircleConnectable should be(false)

    CrossStock.isCircleConnectable should be(false)

    LineStock.isCircleConnectable should be(false)
    LineStockWithDouble.isCircleConnectable should be(false)

    CircleStock.isCircleConnectable should be(true)
    CircleStockWithDouble.isCircleConnectable should be(true)
  }

}
