package wtf.scala.e03.lecture

import math._

object FunctionCompositions extends App {
  def f(str: String) = str + " foo"
  def g(str: String) = str + " bar"

  val fooThenBar = g _ compose f _ // g(f(x)) == addBar(addFoo(s))
  println(fooThenBar("scala")) // scala foo bar

  val barThenFoo = g _ andThen f _ // f(g(x)) == addFoo(addBar(x))
  println(barThenFoo("scala")) // scala bar foo

  val inc = (x: Int) => x + 1 // Int => Int
  val toStr = (x: Int) => s"my int is $x" // Int => String

  val incToStr = toStr compose inc
  println(incToStr(5)) // my int is 6

  //val strToInc = toStr andThen inc

//  val f: A => B
//
//  val gf = g compose(f: C => A) // C => B
//  val hg = g andThen(h: B => C) // A => C
//
//  f compose g == g andThen f
}

object PureFunctions {
  var x = 0

  def foo(z: Int) = {
    val res = z + x
    x += z
    res
  }
  def bar(z: Int) = z + x
  def fun(x: Double, y: String) = sin(x) + y.length
}





