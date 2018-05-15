package wtf.scala.e07

case class Complex(x: Double, y: Double) {
  def abs: Double = x * x + y * y

  def sqr: Complex = Complex(x * x - y * y, 2 * x * y)

  def +(c: Complex): Complex = Complex(x + c.x, y + c.y)
}