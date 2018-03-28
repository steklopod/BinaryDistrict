package wtf.scala.e02

package object lecture {
  val VersionString = "1.0"

  type StringMap[+T] = Map[String,T]
  type JList[T] = java.util.List[T]

  def doSmth(str: String): String = "hahaha"// ...

  //implicit def a2b(a: A): B = // ...
}
