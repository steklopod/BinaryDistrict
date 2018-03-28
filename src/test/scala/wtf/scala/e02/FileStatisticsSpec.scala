package wtf.scala.e02

import org.scalatest.{FunSuite, Matchers}

class FileStatisticsSpec extends FunSuite with Matchers {

  val epsilon = 0.0001

  test("Should parse string with valid Int") {
    val res = FileStatistics.parseIntOpt("123")
    res should be('defined)
    res.get should equal(123)
  }

  test("Should return None for string which can not be parsed") {
    val res = FileStatistics.parseIntOpt("xxx")
    res should be('empty)
  }

  test("Should calculate statistics for correct file") {
    val resOpt = FileStatistics.calculateStatistics("good_numbers.txt")
    resOpt.isSuccess should be(true)

    val res = resOpt.get
    res.average should equal(7.7 +- epsilon)
    res.variance should equal(27.81 +- epsilon)
  }

  test("Should calculate statistics for file with errors") {
    val resOpt = FileStatistics.calculateStatistics("good_numbers_with_error.txt")
    resOpt.isSuccess should be(true)

    val res = resOpt.get
    res.average should equal(11d +- epsilon)
    res.variance should equal(141.6667 +- epsilon)
  }

  test("Should return None for incorrect file") {
    FileStatistics.calculateStatistics("incorrect_file.txt").isFailure should be(true)
  }

  test("Should return None for non existing file") {
    FileStatistics.calculateStatistics("none").isFailure should be(true)
  }

}
