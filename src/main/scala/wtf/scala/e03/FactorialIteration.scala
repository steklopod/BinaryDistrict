package wtf.scala.e03

object FactorialIteration {

  /**
    * As we know, tail recursive function can be optimized into iteration
    * Here you need to create iterational version of factorial yourself
    *
    * @param n - number to compute factorial for
    * @return
    */
  def factorial(n: Long): Long = {
    if (n <= 1) {
      1
    } else {
      n * factorial(n - 1)
    }
  }

}
