package wtf.scala.e06.lecture

object StringExamples extends App {

  "scala".map(_.toUpper)
  "scala".zipWithIndex
  "scala"(3)

  val ops: scala.collection.immutable.StringOps = "hello"
  ops.reverse

  val seq: Seq[Char] = new scala.collection.immutable.WrappedString("hi!")
  seq.reverse

  val str = "scala"

  val opps = str.reverse

  val wr: Seq[Char] = str.reverse

  val builder = new StringBuilder
  builder += 'h'
  builder ++= "ello"
  builder(2)
  builder.toString

  "scala".stripPrefix("sca")

  val b = new StringBuilder("java")
  b.capitalize
}
