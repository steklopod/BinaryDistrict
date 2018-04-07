package wtf.scala.e04.lecture

object Implicits3 extends App {
  class A(val n: Int)
  object A {
    implicit def str(a: A) = s"A: ${a.n}"
  }
  class B(val x: Int, y: Int) extends A(y)
  val b = new B(5, 2)
  val s: String = b
  println(s)
}
