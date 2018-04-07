package wtf.scala.e04.lecture

object Types4 extends App {
  // contravariant
  class Container[-T](t: T) {
    override def toString: String = t.toString
  }
  val s: Container[Dog] = new Container[Animal](new Dog)
  println(s)
//  val s2: Container[Animal] = new Container[Dog](new Dog)
//  println(s2)
}
