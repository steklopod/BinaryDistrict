package wtf.scala.e05.lecture

import scala.collection.mutable.ArrayBuffer

object SeqExample extends App {


  List(3, 5, 1, 8, 2).sorted
  List("cf", "a", "c", "be").sorted

  List(3, 5, 1, 8, 2).sorted(Ordering.fromLessThan[Int](_ > _))

  List(3, 5, 1, 8, 2).sortWith(_ > _)

  List(3, 5, 1, 8, 2).sortBy(_ > 3)


  List(3, 5, 1, 8, 2).reverse
  List(3, 5, 1, 8, 2).reverseIterator
  List(3, 5, 1, 8, 2).reverseMap(_ + 1)


  val buf = new ArrayBuffer[Int]()

  buf += 0

  val l = List(1, 2, 3)
  val ll = 1 :: 2 :: Nil

  val str = 1 #:: 2 #:: 3 #:: Stream.empty

  def fibFrom(a: Int, b: Int): Stream[Int] = {
    a #:: fibFrom(b, a + b)
  }

  val fibs = fibFrom(1, 1).take(7)
  fibs.toList

  val vec = Vector(1, 2, 3)
  vec updated (2, 4)


  1 to 3
  5 to 14 by 3
  1 until 3

  val mutableList = scala.collection.mutable.MutableList(3, 4, 5)
  mutableList += 1

  val queue = new scala.collection.mutable.Queue[String]
  queue += "a"
  queue ++= List("b", "c")
  queue.dequeue



}
