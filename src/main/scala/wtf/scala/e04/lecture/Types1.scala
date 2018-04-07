package wtf.scala.e04.lecture

object Types1 extends App {
  class Container[T](t: T) {
    override def toString: String = t.toString
  }
  val s = new Container[Int](1)
  println(s)
  val s2 = new Container[String]("1!")
  println(s2)
}
