package wtf.scala.e04.lecture

import java.time.LocalDateTime


//https://habr.com/post/329600/
//https://habr.com/post/354028/
/**
  * Ключевое слово implicit имеет отношение к трем понятиям в Scala:
  * a) неявные параметры;
  * b) неявные преобразования;
  * c) неявные классы.
  */

object ExtensionMethods extends App {
  implicit class RichInt(val self: Int) extends AnyVal {
    def toFunny: String = s"$self :P"
  }
  println(1.toFunny)


  implicit def dateTime: LocalDateTime = LocalDateTime.now()

  def printCurrentDateTime(implicit dt: LocalDateTime) = println(dt.toString)

  printCurrentDateTime
  Thread.sleep(1000)
  printCurrentDateTime


}
