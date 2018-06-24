package wtf.scala.e01

object IntegerRoot {

  /**
    * Assume x is non negative integer.
    * If x = y * y for some integer y, then return y, otherwise return -1
    * @param x non negative integer to calculate square root
    * @throws IllegalArgumentException if negative number passed (Hint: check require method)
    * @return -1 if x is not square of some integer
    *         square root of x if x is square of some integer
    */
  @throws[IllegalArgumentException]
  def calculateRoot(x: Int): Int = {
    require(x > 0, "x must be positive.")
    (0 to x/2).find(y => y * y == x).getOrElse(-1)
  }
}
