package wtf.scala.e03.lecture

import scala.util.control.Exception._

object PartialFunctions extends App {
  val foo: PartialFunction[Int, Int] = {
    case pos if pos > 0 => pos * pos
  }

  val bar: PartialFunction[Int, Int] = {
    case neg if neg < 0 => neg + neg
  }

  foo.isDefinedAt(4) // true
  foo(4) // 16

  foo.isDefinedAt(-4) // false
  foo(-4) // scala.MatchError: -4 (of class java.lang.Integer)

  val chained = foo.orElse(bar) // PartialFunction[Int, Int]
  chained.isDefinedAt(4) // true
  chained(4) // 16
  chained.isDefinedAt(-4) // true
  chained(-4) // -8

  chained.isDefinedAt(0) // false
  chained(0) // scala.MatchError: 0 (of class java.lang.Integer)

  def handleIae: PartialFunction[Throwable, Any] = {
    case iae: IllegalArgumentException => System.out.println("Got iae")
  }

  def handleNpe: PartialFunction[Throwable, Any] = {
    case iae: NullPointerException => System.out.println("Got npe")
  }

  try {
    // do something
  } catch handleIae.orElse(handleNpe)
}

object Exceptions {

  def assertPos(x: Int): Int =
    if (x >= 0) x else throw new IllegalArgumentException(s"$x is negative")

  catching(classOf[IllegalArgumentException]) opt assertPos(1)

  catching(classOf[IllegalArgumentException]) opt assertPos(-1)

  catching(classOf[NullPointerException]) opt assertPos(-1)

  def handleIae: PartialFunction[Throwable, Int] = {
    case iae: IllegalArgumentException =>
      System.out.println("Got iae")
      0
  }

  catching(handleIae)(assertPos(-1))
}