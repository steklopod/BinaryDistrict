package wtf.scala.e07

case class MandelbrotParams(imageWidth: Int,
                            imageHeight: Int,
                            xMin: Double,
                            xMax: Double,
                            yMin: Double,
                            yMax: Double,
                            maxIterations: Int) {
  val xStep = if (xMin >= 0 && xMax >= 0) {
    (xMin + xMax) / imageWidth
  } else if (xMin < 0 && xMax >= 0) {
    (xMax - xMin) / imageWidth
  } else {
    (-xMin + xMax) / imageWidth
  }

  val yStep = if (yMin >= 0 && yMax >= 0) {
    (yMin + yMax) / imageHeight
  } else if (xMin < 0 && yMax >= 0) {
    (yMax - yMin) / imageHeight
  } else {
    (-yMin + yMax) / imageHeight
  }
}