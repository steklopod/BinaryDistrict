package wtf.scala.e08.mandelbrot

import wtf.scala.e07.{MandelbrotParams, MandelbrotSetBuilder}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Multidirectional generator of the Mondelbrot set.
  * For now use parallel collections to solve it.
  * @param segments The number of segments on which all the calculation tasks should be divided
  * @param ec a thread pool in which the calculation flows are to be executed
  */
class MultiThreadParSeqGenerator(segments: Int)(implicit ec: ExecutionContext) extends MandelbrotSetBuilder {
  override def apply(params: MandelbrotParams): Future[Seq[Seq[Int]]] = ???
}