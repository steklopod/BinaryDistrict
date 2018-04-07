package wtf.scala.e04.lecture

object CallBy extends App {
  def randomJoke(): Int = {
    println("chosen by fair dice roll. guaranteed to be random")
    1
  }

  def callByValue(x: Int) = {
    println("callByValue")
    println(s"x1 = $x")
    println(s"x2 = $x")
  }

  def callByName(x: => Int) = {
    println("callByName")
    println(s"x1 = $x")
    println(s"x2 = $x")
  }

  callByValue(randomJoke())
  println()
  callByName(randomJoke())
}
