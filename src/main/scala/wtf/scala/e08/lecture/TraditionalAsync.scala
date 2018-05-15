package wtf.scala.e08.lecture

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.ExecutionContext
import scala.util.Random

object ThreadExample extends App {

  println(s"Hi! I am ${Thread.currentThread().getName}")
  val t = new Thread() {
    override def run(): Unit = {
      println(s"Hello! My name is ${Thread.currentThread().getName}")
      Thread.sleep(1000)
    }
  }
  t.start()
  t.join()
  println("New thread joined.")
}

object SynchronizedExample extends App {

  class Person(var name: String) {
    def set(changedName: String) {
      this.synchronized {
        name = changedName
      }
    }
  }

  class X {
    private val lock = new Object()

    lock.synchronized {
      println("wow")
    }
  }

}


object VolatileExample extends App {
  @volatile var running = true
  for (i <- 1 to 5) {
    val t = new Thread {
      override def run(): Unit = {
        while (running) {
          println(s"Still running ${Thread.currentThread().getName}")
          Thread.sleep(1000)
          if (Random.nextDouble() < 0.05) running = false
        }
      }
    }
    t.start()
  }
  while (running) {}
  println("Finished")
}


object AtomicExecutorEtcExample extends App {

  val a = new AtomicInteger(10)
  a.compareAndSet(10, 11)

  val ex = Executors.newFixedThreadPool(10)
  implicit val ec = ExecutionContext.fromExecutor(ex)

}