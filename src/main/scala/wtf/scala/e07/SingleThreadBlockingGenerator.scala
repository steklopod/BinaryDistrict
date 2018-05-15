package wtf.scala.e07

import scala.concurrent.Future

class SingleThreadBlockingGenerator extends MandelbrotSetBuilder {
  override def apply(params: MandelbrotParams): Future[Seq[Seq[Int]]] = {
    import params._

    val result = for {
      y0 <- 0 until imageHeight
    } yield for {
      x0 <- 0 until imageWidth
    } yield {
      val xToCheck = xMin + x0 * xStep
      val yToCheck = yMin + y0 * yStep
      val complexValueToCheck = Complex(xToCheck, yToCheck)
      calculateMandelbrotElement(complexValueToCheck, maxIterations = params.maxIterations)
    }
    Future.successful(result)
  }
}