package wtf.scala.e05.lecture

import scala.reflect.ClassTag

object ArrayExample extends App {

  val letters: Array[String] = Array("a", "b", "c", "d")
  letters.length // 4
  letters(2) // c
  letters(3) = "e"

  letters.map(_.toUpperCase)

  def foo(seq: Seq[String]) = seq.mkString("-")
  foo(letters)

  val arr = Array(1, 2, 3)
  val arrReversed = arr.reverse // Array[Int]
  val seqReversed : Seq[Int] = arr.reverse

  //def getArrayWrong[T](size: Int): Array[T] = new Array[T](size) // Compilation error

  def getArray[T: ClassTag](size: Int): Array[T] = new Array[T](size)

}
