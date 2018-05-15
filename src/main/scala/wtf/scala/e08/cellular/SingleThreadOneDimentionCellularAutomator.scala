package wtf.scala.e08.cellular

import scala.concurrent.Future

class SingleThreadOneDimentionCellularAutomator(override val rule: Byte => Boolean,
                                                override val localitySize: Int) extends OneDimentionCellularAutomator {
  private def calculateNextState(currentState: Seq[Boolean]): Seq[Boolean] = {
    for {
      x <- currentState.indices
    } yield rule(localityStates(x, currentState))
  }

  override def calculateNextStates(currentState: Seq[Boolean], n: Int): Future[Seq[Seq[Boolean]]] = {
    Future.successful(Iterator.iterate(currentState)(calculateNextState).take(n).toSeq)
  }
}