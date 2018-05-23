package wtf.scala.e08.mandelbrot

import wtf.scala.e07.{Complex, MandelbrotParams, MandelbrotSetBuilder}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Multidirectional generator of the Mondelbrot set.
  * For now use parallel collections to solve it.
  *
  * @param segments The number of segments on which all the calculation tasks should be divided
  * @param ec       a thread pool in which the calculation flows are to be executed
  */
class MultiThreadParSeqGenerator(segments: Int)(implicit ec: ExecutionContext) extends MandelbrotSetBuilder {
  override def apply(params: MandelbrotParams): Future[Seq[Seq[Int]]] = Future {
    import params._

    val segmentHeight = params.imageHeight / segments

    val fs = for {
      segment <- (1 to segments).par
      y0 <- ((segment - 1) * segmentHeight) to (segment * segmentHeight)
    } yield {
      for {
        x0 <- 0 until imageWidth
      } yield {
        val xToCheck = xMin + x0 * xStep
        val yToCheck = yMin + y0 * yStep
        val complexValueToCheck = Complex(xToCheck, yToCheck)
        calculateMandelbrotElement(complexValueToCheck, maxIterations = params.maxIterations)
      }
    }

    fs.toList
  }
}