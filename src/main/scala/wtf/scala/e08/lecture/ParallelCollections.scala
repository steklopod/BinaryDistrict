package wtf.scala.e08.lecture

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{CopyOnWriteArrayList, ForkJoinPool}

import scala.collection.parallel.ForkJoinTaskSupport
import scala.collection.{GenSeq, concurrent, mutable}

object ParallelCollections extends App {

  def doSmth(s: GenSeq[Int]) = s.map(_ * 2).sum

  val imList = List(1, 2, 3, 4).par
  val mutList = scala.collection.mutable.MutableList(1, 2, 3, 4).par

  imList.filter(_ > 2)

  mutList.filter(_ > 2)

  val pool = new ForkJoinPool(4)
  val myTaskSupport = new ForkJoinTaskSupport(pool)
  val numbers = Vector.tabulate(5000)(i => i)
  val parNumbers = numbers.par
  parNumbers.tasksupport = myTaskSupport


  Vector(1, 2, 3).foldLeft("")((n, s) => n + s.toString)
  Vector(1, 2, 3).aggregate("")((n, s) => n + s.toString, (s1, s2) => s1 + s2)

  Vector.tabulate(5000)(i => i).par.find(_ > 10)
  Vector.tabulate(5000)(i => i).par.indexWhere(_ > 10)

  val uid = new AtomicInteger(0)
  (0 until 10).par.map(x => uid.incrementAndGet())

  Vector(1, 2, 3, 4).reduce(_ - _)
  Vector(1, 2, 3, 4).par.reduce(_ - _)

  Vector(1, 2, 3, 4).scan(0)(_ + _)


}

object TrieMapExample extends App {

  val m = new scala.collection.concurrent.TrieMap[String, Int]
  m("a") = 2
  m("b") = 3
  m.iterator

  m.readOnlySnapshot()
  m.snapshot()


  val cache = new concurrent.TrieMap[Int, String]()
  for (i <- 0 until 100) cache(i) = i.toString
  for ((num, str) <- cache.par) cache(-num) = s"-$str"
  println(s"${cache.keys.toList.sorted}")


  val buf = new mutable.MutableList[Int]()
  for (i <- (0 until 10).par) buf += i

  val safeBuf = new CopyOnWriteArrayList[Int]()
  for (i <- (0 until 10).par) safeBuf.add(i)

}

