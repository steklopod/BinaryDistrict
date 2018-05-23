package wtf.scala.e13

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

package object lecture {
  implicit val dbEc = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor((r: Runnable) => {
    val t = new Thread()
    t.setDaemon(true)
    t
  }))
}
