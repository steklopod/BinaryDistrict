package wtf.scala.e07

import scala.concurrent.{ExecutionContext, Future}

/**
  * Multidirectional generator of the Mondelbrot set.
  * It must divide the calculation of all points into sets of tasks and start their calculation in parallel.
  * When calculating, you need to remember to save a "segment number" to recreate the correct order of results after their calculation.
  * NOTE: you can find method `Future.sequence` useful.
  * @param segments The number of segments on which all the calculation tasks should be divided
  * @param ec a thread pool in which the calculation flows are to be executed
  */
class MultiThreadGenerator(segments: Int)(implicit ec: ExecutionContext) extends MandelbrotSetBuilder {
  override def apply(params: MandelbrotParams): Future[Seq[Seq[Int]]] = ???
}