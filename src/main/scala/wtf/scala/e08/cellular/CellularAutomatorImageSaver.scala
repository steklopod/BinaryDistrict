package wtf.scala.e08.cellular

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class CellularAutomatorImageSaver(format: String, filePath: String) {
  def apply(field: Seq[Seq[Boolean]], size: Int, steps: Int) = {
    val bi = new BufferedImage(size, steps, BufferedImage.TYPE_BYTE_BINARY)

    val g = bi.createGraphics()
    g.setPaint(Color.WHITE)

    for {
      (xs, y) <- field.zipWithIndex
      (v, x) <- xs.zipWithIndex
      if !v
    } {
      g.drawLine(x, y, x, y)
    }

    ImageIO.write(bi, format, new File(filePath))
  }
}