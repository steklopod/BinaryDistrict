package wtf.scala.e02

object TrapezoidalRule {

  /**
    * Calculate definite integral using Trapezoidal Rule
    *
    * https://en.wikipedia.org/wiki/Trapezoidal_rule
    *
    * @param f              function to calculate definite integral
    * @param leftX          left X bound
    * @param rightX         right X bound
    * @param intervalNumber number of intervals
    * @return definite integral of function on the selected interval
    */
  def integrate(f: Double => Double, leftX: Double, rightX: Double, intervalNumber: Int = 100): Double = {
    val step = (rightX - leftX) / intervalNumber
    if (step > 0) {
      (leftX until rightX by step).sliding(2).map { case Seq(a, b) =>
        (f(a) + f(b)) / 2 * (b - a)
      }.sum
    } else {
      0
    }
  }


  def main(args: Array[String]) {
    val result = integrate(cube, 1, 0)
    print(s"Result is $result")
  }

  def cube(x: Double) = x * x * x

}
