package wtf.scala.e06.lecture

import java.util.concurrent.ConcurrentHashMap

import scala.collection.JavaConverters._
import scala.collection.parallel.ForkJoinTaskSupport

object ParUnsafe extends App {
  val str = "A" * 10 + "H" * 20 + "OP" * 30
  val freq = new ConcurrentHashMap[Char, Int].asScala
  val p = str.par
  p.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(4))
  for {
    c <- p
  } {
    freq.putIfAbsent(c, 0)
    def retry = {
      val oldCached = freq.getOrElse(c, 0)
      freq.replace(c, oldCached, oldCached + 1)
    }
    while (!retry) {}
  }
  println(freq)
}
