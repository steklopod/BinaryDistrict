package wtf.scala.e01

object IntegerRoot {

  /**
    * Assume x is non negative integer.
    * If x = y * y for some integer y, then return y, otherwise return -1
    *
    * @param x non negative integer to calculate square root
    * @throws IllegalArgumentException if negative number passed (Hint: check require method)
    * @return -1 if x is not square of some integer
    *         square root of x if x is square of some integer
    */
  @throws[IllegalArgumentException]
  def calculateRoot(x: Int): Int = {
    require(x >= 0, "Значение должно быть больше 0")
    (0 to x).find(y => y*y == x).getOrElse(-1)

    // Второй вариант
    //    (0 to x).filter(y => y*y == x).headOption.getOrElse(-1)

  }

}
