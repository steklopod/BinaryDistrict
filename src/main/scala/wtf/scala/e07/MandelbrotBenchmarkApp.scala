package wtf.scala.e07

import java.util.concurrent.Executors

import wtf.scala._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.io.StdIn
import scala.util.{Success, Try}

object MandelbrotBenchmarkApp extends App {
  val processors = Runtime.getRuntime.availableProcessors()

  val pool = Executors.newFixedThreadPool(processors * 4)
  implicit val ec = ExecutionContext.fromExecutor(pool)

  val singleThreadGenerator = new SingleThreadBlockingGenerator
  val multiThreadGenerator = new MultiThreadGenerator(segments = processors * 10)

  val params = MandelbrotParams(900, 600, -2f, 1f, -1f, 1f, 1000)

  val warmupTimes = 30

  print("Warmup single thread")
  val singleThreadGeneratorTime = warmedTimed(warmupTimes) {
    print(".")
    singleThreadGenerator(params)
  }
  println

  println(s"Single thread generator time: $singleThreadGeneratorTime")

  print("Warmup future multi thread")
  val multiThreadGeneratorTime = warmedTimed(warmupTimes) {
    print(".")
    Await.result(multiThreadGenerator(params), Duration.Inf)
  }
  println

  println(s"Multi thread generator time: $multiThreadGeneratorTime")

  println("Save result to PNG file? [y/n]")
  Try(StdIn.readChar()) match {
    case Success('y') =>
      val mandelbrotElements = Await.result(multiThreadGenerator(params), Duration.Inf)
      val fileName = "mandelbrot.png"
      new MandelbrotSetImageSaver("PNG", fileName).apply(params, mandelbrotElements)
      println(s"See '$fileName' file with mandelbrot set in project folder")
    case _ =>
  }
  pool.shutdown()
}