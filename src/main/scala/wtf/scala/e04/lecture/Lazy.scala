package wtf.scala.e04.lecture

object Lazy extends App {
  lazy val soooLazy = {
    println("laaaaazy")
    1
  }
  println("start")
  println(soooLazy)
  println(soooLazy)
}
