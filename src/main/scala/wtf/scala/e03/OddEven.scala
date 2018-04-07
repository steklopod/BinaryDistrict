package wtf.scala.e03

object OddEven {

  /**
    * multiply number by sin of this number if it is Even
    */
  private val modifyEvens: PartialFunction[Int, Double] = ???

  /**
    * multiply number by cos of this number if it is Odd
    */
  private val modifyOdds: PartialFunction[Int, Double] = ???

  /**
    * Return "Positive" if number > 0
    */
  private val printPositive: PartialFunction[Double, String] = ???

  /**
    * Return "Negative" if number < 0
    */
  private val printNegative: PartialFunction[Double, String] = ???

  /**
    * Modify input number depending on if it is even or not,
    * then pass result to the next partial function which returns string depending on the result
    * Implement private functions provided below then compose them to implement process function
    */
  val process: PartialFunction[Int, String] = ???

}
