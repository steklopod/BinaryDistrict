package wtf.scala.e04.lecture

object Correctness extends App {
  class Meter(val value: Double) extends AnyVal {
    def +(m: Meter): Meter = new Meter(value + m.value)
  }

  val x = new Meter(3.4)
  val y = new Meter(4.3)
  val z = x + y
//  val zz = x + 3.1
}
