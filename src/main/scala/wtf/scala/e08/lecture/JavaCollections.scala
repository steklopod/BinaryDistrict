package wtf.scala.e08.lecture

import java.util
import java.util.concurrent.ConcurrentHashMap



object JavaConvertersExample extends App {

  import scala.collection.JavaConverters._

  val jList = new util.ArrayList[String](util.Arrays.asList("scala", "java"))
  jList.asScala

  val list = List("scala", "java")
  list.asJava




  val map = new ConcurrentHashMap[String, String] asScala

}

object JavaConversionsExample extends App {

  import scala.collection.JavaConversions._

  val jList = new util.ArrayList[String](util.Arrays.asList("scala", "java"))

  jList.map(_.toUpperCase)

}
