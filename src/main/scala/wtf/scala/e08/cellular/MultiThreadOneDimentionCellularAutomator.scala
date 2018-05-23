package wtf.scala.e08.cellular

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

class MultiThreadOneDimentionCellularAutomator(segments: Int,
                                               override val rule: Byte => Boolean,
                                               override val localitySize: Int)(implicit ec: ExecutionContext) extends OneDimentionCellularAutomator {
  private def calculateNextState(currentState: Seq[Boolean]): Seq[Boolean] = {
    require((currentState.size.toDouble / segments) % 1 == 0)
    val segmentSize = currentState.size / segments
    val fs = for {
      segment <- 1 to segments
    } yield Future {
      (segment, for {
        x <- (segment - 1) * segmentSize until segment * segmentSize
      } yield {
        // todo start calculation of the lower neighbors
        rule(localityStates(x, currentState))
      })
    }
    // parallelization only occurs within one step
    val results = Await.result(Future.sequence(fs), Duration.Inf)
    results.sortBy(_._1).flatMap(_._2)
  }

  override def calculateNextStates(currentState: Seq[Boolean], n: Int): Future[Seq[Seq[Boolean]]] = {
    Future.successful(Iterator.iterate(currentState)(calculateNextState).take(n).toSeq)
  }
}