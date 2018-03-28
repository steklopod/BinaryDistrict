package wtf.scala.e02.lecture

object OOPExamples {

  class Person(firstName: String, lastName: String, age: Int) {
    import Person._

    def greet(question: String) = hello + firstName + "!" + question
  }

  object Person {
    private val hello = "Hello, "

    def apply(firstName: String, lastName: String, age: Int): Person =
      new Person(firstName, lastName, age)
  }

  val john = new Person("John", "Smith", 25)
  val william = Person("WilliaM", "Smith", 47)

  abstract class Vehicle(brand: String, maxSpeed: Double) {
    val numWheels: Int
    def ride(): Unit
    def stop(): Unit = println("Stop!")
  }

  trait VehicleLike {
    val numWheels: Int
    def ride(): Unit
    def stop(): Unit = println("Stop!")
    val brand: String
    val maxSpeed: Double
  }

  trait Cargo {
    def carryStuff(s: Any): Unit = println(s"$s is on board!")
  }

  class Moto(val brand: String, val maxSpeed: Double) extends VehicleLike {
    override val numWheels: Int = 2
    override def ride(): Unit = println("Woom")
  }

  class Truck(val brand: String, val maxSpeed: Double) extends VehicleLike with Cargo {
    override val numWheels: Int = 4
    override def ride(): Unit = println("Wrrrr")
  }

  class Car(brand: String, maxSpeed: Double, numSeats: Int) extends Vehicle(brand, maxSpeed) {
    override val numWheels: Int = 4
    override def ride(): Unit = {
      println("wrrrooom")
    }
  }

  object MyObject {
    val x = "scala"
    def foo(str: String) = s"hi, $str"
  }

  object InheritedObject extends Serializable


  class Foo(a: Int, val b: Int, var c: Int) { def sum = a + b + c }

  object FooTest extends App {
    val x = new Foo(1, 2, 3)
    println(x.b)
    println(x.c)
    println(x.sum)
    x.c = 8
    println(x.sum)
  }

  trait RunOne { def run(): Unit = println("Run") }
  trait RunTwo { def run(): Unit = println("Run, Forest!") }

  class GoodRunner extends RunOne with RunTwo {
    override def run(): Unit = println("Run, Forest, run!")
  }

  object Season extends Enumeration {
    val Winter, Spring, Summer, Autumn = Value
  }

  Season.maxId
  Season.values
  Season.Winter.id
  Season.Winter + Season.Spring
  Season.Winter < Season.Spring

}
