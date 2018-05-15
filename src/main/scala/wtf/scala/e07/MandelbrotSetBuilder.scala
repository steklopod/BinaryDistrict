package wtf.scala.e07

import scala.annotation.tailrec
import scala.concurrent.Future

trait MandelbrotSetBuilder {
  def apply(params: MandelbrotParams): Future[MandelbrotSet]

  protected final def calculateMandelbrotElement(point: Complex,
                                                 maxIterations: Int = 1000
                                                ): Int = {
    @tailrec
    def iterate(i: Int = 0, z: Complex = Complex(0, 0)): Int = {
      if (i < maxIterations && z.abs < 4) {
        val newPoint = z.sqr + point
        iterate(i + 1, newPoint)
      } else {
        if (i == maxIterations) -1 else i
      }
    }
    iterate()
  }
}