package wtf.scala.e08.cellular

import scala.concurrent.Future

trait CellularAutomator {
  type State
  type Locality
  type Field <: Seq[State]
  type Position
  type Rule = Locality => State

  def rule: Rule

  def localitySize: Int

  def calculateNextStates(currentState: Field, n: Int): Future[Seq[Field]]
}