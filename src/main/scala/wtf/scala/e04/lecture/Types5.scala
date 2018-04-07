package wtf.scala.e04.lecture

object Types5 extends App {
  val getCatSound: (Cat => String) = (a: Animal) => a.sound
//  val getAnimalSound: (Animal => String) = ((a: Cat) => a.sound)
  val catBirth: (() => Animal) = () => new Cat
//  val birth: (() => Animal) = () => new AnyRef
}
