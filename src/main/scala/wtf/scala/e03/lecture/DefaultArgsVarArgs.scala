package wtf.scala.e03.lecture

object DefaultArgs extends App {

  case class MyClass(x: Int, y: Int)

  val a = MyClass(1, 2)
  val b = a.copy(y = 3)

  def foo(x: String = "scala", y: String = "rules") = x + y.toUpperCase

  foo("java", "magic")
  foo()
  foo("Java")
  foo(y = ".wtf")

  def bar(x: String, y: String = "Martin") = x + y

  bar("hello")
  val fooFun = foo _

}

object VarArgs extends App {

  def foo(args: String*) = args.mkString(",")
  foo("x") // x
  foo("x", "y") // x,y

  val seq = Seq("x", "y", "z") // // Для Seq в varargs используется : _*
  foo(seq: _*) // x,y,z

  val fooFun = foo _ // Seq[String] = > String
  //fooFun("x")
  fooFun(Seq("x", "y", "z")) // x,y,z

}