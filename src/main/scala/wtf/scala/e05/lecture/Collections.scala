package wtf.scala.e05.lecture

object TraversableExample {

  val convertFn: PartialFunction[Any, Int] = {
    case i: Int => i;
    case s: String => s.toInt;
    case Some(s: String) => s.toInt
  }

  List(0, 1.3, "2", "3", Some(4), Some("5"), true).collect(convertFn) // List[Int] = List(0, 2, 3, 5)

}

object IteratorExample extends App {

  val it = Iterator(1, 2, 3) // Iterator[Int] = non-empty iterator
  while (it.hasNext) {
    println(it.next())
  }
  it // Iterator[Int] = empty iterator

  val it2 = Iterator("a", "b", "c")
  val upperCaseIt = it2.map(_.toUpperCase)
  it2.hasNext // true

  it2.foreach(println)
  it2.hasNext // false

  val it3 = Iterator("one", "two", "three")
  val (copy1, copy2) = it3.duplicate

  val shortWordsNum = copy1.count(_.length < 4) // 2

  val totalLetters = copy2.map(_.length).sum // 11
}

object IterableExample {

  val groupedIt = List(1, 2, 3).grouped(2) // Iterator[List[Int]] = non-empty iterator
  groupedIt.next // List[Int] = List(1, 2)
  groupedIt.next // List[Int] = List(3)

  val slidingIt = List(1, 2, 3).sliding(2) // Iterator[List[Int]] = non-empty iterator
  slidingIt.next // List(1, 2)
  slidingIt.next // List(2, 3)

  List(1, 2, 3) zip List("a", "b", "c")

  List(1, 2, 3) zipAll(List("a", "b"), 0, "x")

  List(1) zipAll(List("a", "b"), 0, "x")

  List("a", "b", "c").zipWithIndex
}

