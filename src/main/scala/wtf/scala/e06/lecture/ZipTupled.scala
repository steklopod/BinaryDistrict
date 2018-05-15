package wtf.scala.e06.lecture

object ZipTupled extends App {
  val prices = Seq(0.2, 1, 5)
  val quantities = Seq(3, 7, 4)
  val p1 = (prices zip quantities) map { p => p._1 * p._2 }
  val p2 = (prices zip quantities) map ((_: Double) * (_: Int)).tupled

  println(p1)
  println(p2)
}
