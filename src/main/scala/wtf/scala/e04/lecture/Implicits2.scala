package wtf.scala.e04.lecture

import scala.language.implicitConversions

object Implicits2 extends App {
  case class MyString(s: String) {
    def whose = s"I'm yours :] $s"
  }
  implicit def smtn(implicit x: MyString): Unit = println(x.whose)
  implicit val mine: MyString = MyString("!11")
  smtn
}
