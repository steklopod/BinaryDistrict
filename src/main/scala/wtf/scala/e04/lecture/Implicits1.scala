package wtf.scala.e04.lecture

import scala.language.implicitConversions

object Implicits1 extends App {

  case class MyString(s: String) {
    def whose = s"I'm yours :] $s"
  }

  implicit def strToMyString(x: String): MyString = MyString(x)

  val mine: MyString = "!11"

  println("heh".whose)
}
