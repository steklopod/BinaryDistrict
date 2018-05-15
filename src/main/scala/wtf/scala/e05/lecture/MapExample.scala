package wtf.scala.e05.lecture

object MapExample extends App {

  val map = Map((1, "a"), (2, "b"), (3, "c"))
  val sugarMap = Map(1 -> "a", 2 -> "b", 3 -> "c")
  Map(1 -> "a", 2 -> "b").toList

  map.get(1) // Some("a")
  map.get(5) // None
  map(1) // a
  map(5) // java.util.NoSuchElementException: key not found: 5
  map.getOrElse(5, "d") // d
  map.contains(1) // true
  map.contains(5) // false

  Map(1 -> "a") + (2 -> "b") // Map(1 -> a, 2 -> b)
  Map(1 -> "a") + (2 -> "b", 3 -> "c") // Map(1 -> a, 2 -> b, 3 -> c)
  Map(1 -> "a") - 1 // Map()

  Map(1 -> "a") + (1 -> "b") // Map(1 -> b)
  Map(1 -> "a") ++ Map(1 -> "b") // Map(1 -> b)

  Map(1 -> "a", 2 -> "b").map { x =>
    (x._1 - 1 , x._2.length)
  }

  Map(1 -> "a", 2 -> "b").map {
    case (k, v) => (k + 1, v.toUpperCase)
  }

  Map(1 -> "a", 2 -> "b").mapValues(_.contains("a"))

  Map(1 -> "a", 2 -> "b").filter {
    case (k, v) => (k > 1) && (v != "b")
  }

  Map(1 -> "a", 2 -> "b").filterKeys(_ > 1)

  val mMap = scala.collection.mutable.Map(1 -> "a", 2 -> "b")

  mMap(3) = "c"
  mMap.put(4, "d")
  mMap += (5 -> "e")
  mMap -= 2

  mMap.retain((k, v) => k > 4)
  mMap

  mMap.clear()
  mMap
}