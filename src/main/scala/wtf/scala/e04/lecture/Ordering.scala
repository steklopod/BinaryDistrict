package wtf.scala.e04.lecture

object Ordering extends App {

  abstract class Animal {
    def say: String
  }

  case class Bird(override val say: String) extends Animal

  case class Cat(override val say: String) extends Animal

  case class Dog(override val say: String) extends Animal

  case class Fish(override val say: String) extends Animal

  val a: Seq[Animal] = Seq(
    Bird("Чирик"),
    Bird("ААААААаАААаААаАААа"),
    Cat("МурМур"),
    Dog("ГавГавГавГавГавГавГавГавГавГавГавГавГавГав"),
    Fish("!")
  )

  implicit val animalsOrdering: Ordering[Animal] = new Ordering[Animal] {
    override def compare(x: Animal, y: Animal) = x.say.length.compare(y.say.length)
  }

  println(a.sorted)
}
