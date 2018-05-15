package wtf.scala.e06.lecture

import java.util.TimeZone

object TimeZones extends App {
  println(TimeZone.getAvailableIDs
    .groupBy(id => id.split("/").head)
    .mapValues(_.length).toSeq.sortBy(-_._2))
}
