package wtf.scala.e07

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class MandelbrotSetImageSaver(format: String, filePath: String) {
  def apply(params: MandelbrotParams, set: MandelbrotSet): Unit = {
    val bi = new BufferedImage(params.imageWidth, params.imageHeight, BufferedImage.TYPE_INT_RGB)

    val g = bi.createGraphics()

    import params._

    val histogram = set.flatten
      .groupBy(identity)
      .map(g => (g._1, g._2.size))
      .filter(_._1 >= 0)
      .map(g => (g._1 - 1, g._2))
      .withDefaultValue(0)

    val total = histogram.values.sum

    for {
      px <- 0 until imageWidth
      py <- 0 until imageHeight
    } {
      val numIters = set(py)(px)

      var colorVal = 0f
      for (i <- 0 until numIters) {
        colorVal += histogram(i) / total.toFloat
      }
      val rgb = Color.HSBtoRGB(0.1f + colorVal, 1.0f, colorVal * colorVal)

      g.setPaint(new Color(rgb))
      g.drawLine(px, py, px, py)
    }

    ImageIO.write(bi, format, new File(filePath))
  }
}