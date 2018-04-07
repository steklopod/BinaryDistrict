package wtf.scala.e04.lecture

object ValueClass extends App {
  class Wrapper(val underlying: Int) extends AnyVal {
    def foo: Int = underlying * 19
  }
  val i = new Wrapper(123)
  println(i.foo)
}
