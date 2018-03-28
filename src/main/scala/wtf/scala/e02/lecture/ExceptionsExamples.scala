package wtf.scala.e02.lecture

import scala.util.{Failure, Success, Try}

object ExceptionsExamples {

  def handler: PartialFunction[Throwable, Any] = {
    case iae: IllegalArgumentException =>
      System.out.println("Got iae")
    case ex: RuntimeException =>
      System.out.println("Got ex" + ex.getMessage)
  }

  try {

  } catch handler

  def exception(): Unit = {
    try {
      // Some stuff
    } catch {
      case iae: IllegalArgumentException =>
        iae.printStackTrace()
      case ex: Exception =>
        ex.printStackTrace()
    } finally {

    }

    val x = try {
      Integer.parseInt("10")
    } catch {
      case ex: Exception =>
        0
    }
  }

  def checkMoney(money: Int): Unit = {
    if (money < 0) {
      throw new RuntimeException("Insufficient funds")
    }
  }

  Try(Integer.parseInt("1")) match {
    case Success(number) => println(number)
    case Failure(ex) => ex.printStackTrace()
  }
}

object TryExample {
  val success = Try(Integer.parseInt("1"))
  val failure = Try(Integer.parseInt("oops"))

  success.map(i => i + 1) // Success(2)
  failure.map(i => i + 1) // Failure(java.lang.NumberFormatException: For input string: "oops")

  success.toOption // Some(1)
  failure.toOption // None
}

object EitherExample {
  val right: Either[String, Int] = Right(10)
  val left: Either[String, Int] = Left("LEFT")

  val x = right.map(s => s"I've got $s") // Right(I've got 10)
  val y = left.map(s => s"I've got $s") // Left(LEFT)
  val z = left.left.map(s => s"I've got $s") // Left(I've got LEFT)
}
