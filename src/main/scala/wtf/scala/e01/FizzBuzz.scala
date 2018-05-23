package wtf.scala.e01

object FizzBuzz {

  /**
    * Return "fizz", "buzz", "fizzbuzz" of number between 0 (inclusive) and n (exclusive).
    * For a given natural number greater than zero return:
    *   "fizzbuzz" if the number is dividable by 3 and 5
    *   "buzz" if the number is dividable by 5
    *   "fizz" if the number is dividable by 3
    * the same number if no other requirement is fulfilled
    * @param n
    * @throws IllegalArgumentException if n < 1
    * @return seq of fizzbuzz strings
    */
  @throws[IllegalArgumentException]
  private[e01] def fizzBuzzUntil(n: Int): Seq[String] = {
    require(n >= 1, "n must be greater than 1")
    (0 until n) map {
      case n if n % 15 == 0 => "fizzbuzz"
      case n if n % 5 == 0 => "buzz"
      case n if n % 3 == 0 => "fizz"
      case n => n.toString
    }
  }

  def main(args: Array[String]): Unit = {
    println(fizzBuzzUntil(100).mkString(" "))
  }
}
