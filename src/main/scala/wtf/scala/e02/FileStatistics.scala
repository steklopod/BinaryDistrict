package wtf.scala.e02

import scala.util.Try

object FileStatistics {

  case class Statistics(average: Double, variance: Double)

  /**
    * Given a file (in Resources directory) where each line should be Int but there can be broken lines which are not Int
    * Calculate average
    * https://en.wikipedia.org/wiki/Arithmetic_mean
    * and population variance
    * https://en.wikipedia.org/wiki/Variance
    * of these numbers
    * Broken lines (i.e. not integer numbers) should be skipped
    *
    * Use parseIntOpt in your implementation
    *
    * Program should return Some(Statistics) if file with at least one correct row exists, None otherwise
    *
    * Hint: use scala.io.Source.fromResource method to operate with external resources
    *
    * @param fileName - name of input file
    * @return Try of object with numbers' statistics
    */
  def calculateStatistics(fileName: String): Try[Statistics] = {
    ???
  }

  /**
    * Parse String into Int
    * @param str string to parse
    * @return Some[Int] if string can be parsed into Int, None otherwise
    */
  def parseIntOpt(str: String): Option[Int] = ???

}