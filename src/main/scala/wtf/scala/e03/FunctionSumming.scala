package wtf.scala.e03

object FunctionSumming {

  /**
    * Implement method summing values of function f in all integer points of segment [a, b]
    * You should use tail recursion in your implementation
    *
    * Example: Assume we have segment [0,2] and function f = x
    * Then sum(f)(0, 2) = f(0) + f(1) + f(2) = 0 + 1 + 2
    *
    * @param f - function to sum over segment
    * @param a - left bound of segment
    * @param b - right bound of segment
    * @return
    */
  def sumFunction(f: Int => Double)(a: Int, b: Int): Double = {
    if (a == b) {
      f(a)
    } else {
      f(a) + sumFunction(f)(a + 1, b)
    }
  }

}
