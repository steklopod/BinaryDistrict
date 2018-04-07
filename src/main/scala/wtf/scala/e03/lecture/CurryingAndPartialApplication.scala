package wtf.scala.e03.lecture

object Currying {

  def add(x: Int, y: Int) = x + y
  def addC(x: Int)(y: Int) = x + y

  val addX: Int => Int = addC(1)

  add(1, 2) // 3
  addC(1)(2) // 3
  addX(2) // 3
  addX(0) // 1

  val addCurried = (add _).curried

  add(1,2) // 3
  addCurried(1)(2) // 3

  def multCurried(x: Int)(y: Int) = x * y
  val mult = Function.uncurried(multCurried _)

  multCurried(3)(4) // 12
  mult(3, 4) // 12

}

object CurryingType {
  def add(x: Int, y: Int, z: Int) = x + y + z
  val addF = add _ // (Int, Int, Int) => Int

  def addCurried(x: Int)(y: Int)(z: Int) = x + y + z
  val addCurriedF = addCurried _ // Int => (Int => (Int => Int))

}

object WhyCurrying extends App {

  def foo(x: String)(y: String => Unit) = y(x)

  foo("vasia") { name =>
    println(s"hi, $name!")
  }


}

object PartialApplication {
  val sumThree = (x: Int, y: Int, z: Int) => x + y + z
  val res = sumThree(1, 2, 3) // 6

  val sumLastTwo = sumThree(1, _:Int, _:Int) // (Int, Int) => Int
  val res1 = sumLastTwo(2, 3) // 6

  val sumLast = sumThree(1, 2, _:Int) // Int => Int
  val res2 = sumLast(3) // 6
}

object Tupling extends App {

  def foo(x: Int, y: Int) =  x + y
  foo(1, 2) // 3

  val fooTupled = (foo _).tupled // ((Int, Int)) => Int
  fooTupled((1, 2)) // 3
}

object Untupling extends App {
  def bar(x: (Int, Int)) = x._1 + x._2
  bar ((1, 2)) // 3

  val barUntupled = Function.untupled(bar _) // (Int, Int) => Int
  barUntupled(1, 2) // 3
}