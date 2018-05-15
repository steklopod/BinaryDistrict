package wtf.scala.e08.cellular

object OneDimentionCellularAutomator {
  object Rule30 extends OneDimentionCellularAutomator#Rule {
    override def apply(locality: Byte): Boolean = locality match {
      case 7 /*111*/ => false
      case 6 /*110*/ => false
      case 5 /*101*/ => false
      case 4 /*100*/ => true
      case 3 /*011*/ => true
      case 2 /*010*/ => true
      case 1 /*001*/ => true
      case 0 /*000*/ => false
      case v => throw new IllegalArgumentException(s"Value must be < 8 (with 3 cell counts), but $v found")
    }
  }
}
trait OneDimentionCellularAutomator extends CellularAutomator {
  override type State = Boolean
  override type Locality = Byte
  override type Field = Seq[Boolean]
  override type Position = Int

  private val doubleLocality = localitySize * 2

  protected def localityStates(pos: Position, currentState: Seq[Boolean]): Locality = {
    currentState
      .slice(pos - localitySize, pos + localitySize + 1)
      .zipWithIndex
      .foldLeft(0) {
        case (r, (v, index)) => if (v) r | 1 << doubleLocality - index else r
      }.toByte
  }
}