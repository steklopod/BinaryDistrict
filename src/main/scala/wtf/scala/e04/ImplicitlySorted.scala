package wtf.scala.e04

import SortedList._

case class SortMe(first: String, second: BigDecimal)

object SortedList {
  /**
    * Implicitly wraps list to sorted sorted
    * @param list list to sort
    * @param ordered implicit object with ordering rule for type T
    * @tparam T type for sorting
    * @return sorted list
    */
  implicit def list2sorted[T](list: List[T])(implicit ordered: Ordering[T]): SortedList[T] = ???
}

case class SortedList[T](list: List[T])(implicit ordered: Ordering[T]) {
  require(list.sorted == list, "I'm not sorted :<")
}

object ImplicitlySorted extends App {
  def printSortedList(list: SortedList[_]) = println(list)

  printSortedList(List(3, 2, 1))
  printSortedList(List("3", "2", "1"))
  // todo make this works also
  // printSortedList(List(SortMe("abc", BigDecimal(1)), SortMe("abc", BigDecimal(3)), SortMe("bac", BigDecimal(1))))
}
