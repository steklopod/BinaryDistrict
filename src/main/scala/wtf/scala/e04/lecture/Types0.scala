package wtf.scala.e04.lecture

object Types0 extends App {
  class IntContainer(t: Int) {
    override def toString: String = t.toString
  }
  val s = new IntContainer(1)
  println(s)
}
