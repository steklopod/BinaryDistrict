package wtf.scala.e05.lecture

import scala.collection.{BitSet, SortedSet}

object SetExample extends App {

  val mySet = Set(1, 2, 3, 4, 5)

  println(mySet.getClass)

  Set(1, 3, 4).contains(2) // false
  Set(1, 2).subsetOf(Set(1, 2, 3)) // true

  Set(1) + 2 // Set(1, 2)
  Set(1, 2, 3) - (2, 4) // Set(1, 3)
  Set(1, 2) ++ Set(2, 3) // Set(1, 2, 3)

  val myOrder = Ordering.fromLessThan[String](_.length > _.length)
  SortedSet("abc", "c", "ab")(myOrder).foreach(println)

  SortedSet("abc", "c", "ab").foreach(println) // implicit ordering


  val setA = Set(1, 2, 3)
  val setB = Set(2, 3, 4)

  setA.union(setB)
  setA.intersect(setB)

  setA.diff(setB)
  setB.diff(setA)

  val bitSet = BitSet(1, 2, 3)
  bitSet(0)
  bitSet(1)

  def doX(x: String) = x + ":"

  //  val xFunc = doX _

  List("1","2","3").map(doX)

  val mSet = scala.collection.mutable.Set(1, 2)

  mSet += 3
  mSet.add(6)

  mSet -= 1

  mSet.retain(_ > 2)
  mSet.clear()

}