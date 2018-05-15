package wtf.scala.e08.cellular

import scala.concurrent.{ExecutionContext, Future}

class MultiThreadOneDimentionCellularAutomator(segments: Int,
                                               override val rule: Byte => Boolean,
                                               override val localitySize: Int)(implicit ec: ExecutionContext) extends OneDimentionCellularAutomator {
  override def calculateNextStates(currentState: Seq[Boolean], n: Int): Future[Seq[Seq[Boolean]]] = ???
}