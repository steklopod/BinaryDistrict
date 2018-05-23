package wtf.scala.e12

import scala.annotation.tailrec

/**
  * The median is the value separating the higher half of a data sample from the lower half.
  *
  * Here we list a few immutable algorithms for median calculation, having different
  * time/memory complexity.
  *
  * @see [[https://en.wikipedia.org/wiki/Median]]
  * @see [[https://stackoverflow.com/questions/4662292/scala-median-implementation]]
  */
object Median {

  /**
    * This median calculation sorts the input and returns the middle (in case of odd input size),
    * or rather a mean of two middle values (in case of even input size) as a median value.
    *
    * Time complexity is supposed to be O(n*logn) (in case of log-linear sorting algorithm).
    */
  def sortingMedian(arr: Array[Int]): Int = {
    val sortedArr = arr.sortWith(_ < _)

    if (arr.length % 2 == 1) {
      sortedArr(sortedArr.length / 2)
    } else {
      val (up, down) = sortedArr.splitAt(arr.length / 2)
      (up.last + down.head) / 2
    }
  }

  /**
    * K-Median method is quadratic at max, but has linear average, depending on the pivot selection.
    */
  def kMedian(arr: Array[Int])(implicit choosePivot: Array[Int] => Int): Int
    = findKMedian(arr, (arr.length - 1) / 2)

  implicit def chooseRandomPivot(arr: Array[Int]): Int = arr(scala.util.Random.nextInt(arr.length))

  @tailrec private def findKMedian(arr: Array[Int], k: Int)(implicit choosePivot: Array[Int] => Int): Int = {
    val a = choosePivot(arr)
    val (s, b) = arr partition (a >)
    if (s.length == k) a
    // The following test is used to avoid infinite repetition
    else if (s.isEmpty) {
      val (s, b) = arr partition (a ==)
      if (s.length > k) a
      else findKMedian(b, k - s.length)
    } else if (s.length < k) findKMedian(b, k - s.length)
    else findKMedian(s, k)
  }

  /**
    * The median of medians method, which guarantees average linear time, depending on algorithm
    * to find a median of up to five-element array, effectively splitting the input array into
    * 5-length sub-arrays and finding their medians; and doing so recursively.
    *
    * @see [[https://en.wikipedia.org/wiki/Median_of_medians]]
    * @see [[https://stackoverflow.com/questions/4761405/compute-median-of-up-to-5-in-scala]]
    */
  def medianOfMedians(arr: Array[Int]): Int = {
    val medians = arr grouped 5 map medianUpTo5 toArray

    if (medians.length <= 5) medianUpTo5(medians)
    else medianOfMedians(medians)
  }

  private def medianUpTo5(five: Array[Int]): Int = {
    def order2(a: Array[Int], i: Int, j: Int) = {
      if (a(i) > a(j)) {
        val t = a(i); a(i) = a(j); a(j) = t
      }
    }
    def pairs(a: Array[Int], i: Int, j: Int, k: Int, l: Int) = {
      if (a(i) < a(k)) {
        order2(a, j, k); a(j)
      } else {
        order2(a, i, l); a(i)
      }
    }

    if (five.length < 2) {
      return five(0)
    }

    order2(five, 0, 1)

    if (five.length < 4) {
      return if (five.length == 2 || five(2) < five(0)) {
        five(0)
      } else if (five(2) > five(1)) {
        five(1)
      } else {
        five(2)
      }
    }

    order2(five, 2, 3)

    if (five.length < 5) {
      pairs(five, 0, 1, 2, 3)
    } else if (five(0) < five(2)) {
      order2(five, 1, 4)
      pairs(five, 1, 4, 2, 3)
    } else {
      order2(five, 3, 4)
      pairs(five, 0, 1, 3, 4)
    }
  }

}
