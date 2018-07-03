package wtf.scala.e03

object FunctionCompositions {


  /**
    * Implement function that composes the functions
    *
    * @param f - second function to apply
    * @param g - first function to apply
    * @return
    */
  def compose[A, B, C](f: B => C, g: A => B): A => C = a => f(g(a))

  /**
    * Implement function that creates curried version of in input function
    *
    * @param f - function to curry
    * @return
    */
  def curry[A, B, C](f: (A, B) => C): A => (B => C) = a => b => f(a, b)

  /**
    * Implement function that creates uncurried version of curried function f
    *
    * @param f - function to uncurry
    * @return
    */
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = (a, b) => f(a)(b)

  /**
    * Implement modulus function returning modulus of initial function
    *
    * @param f
    * @return
    */
  def modulus(f: Double => Double): Double => Double = a => {
    val result = f(a)
    if (result < 0) -result else result
  }

}
