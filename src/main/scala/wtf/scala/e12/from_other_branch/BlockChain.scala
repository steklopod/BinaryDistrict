package wtf.scala.e12.from_other_branch

import scala.collection.immutable.ListMap
import scala.util.{Failure, Success, Try}

object BlockChain {
  val genesisBlock = Block(None, "ff9320b9-f994-4726-b85b-694c9ed59764", 1)

  def withGenesis: BlockChain = BlockChain().append(genesisBlock).get
}

case class BlockChain private(blocks: ListMap[String, Block] = ListMap.empty) extends Iterable[Block] {

  lazy val lastBlockId: Option[String] = lastBlock.map(_.id)
  lazy val lastBlock: Option[Block] = blocks.lastOption.map(_._2)

  lazy val height: Int = blocks.size

  lazy val score: Long = iterator.map(_.score).sum

  def contains(b: Block): Boolean = iterator.contains(b)

  def append(b: Block): Try[BlockChain] = {
    Block.isValid(b, this) match {
      case Left(error) =>
        Failure(new IllegalArgumentException(s"Invalid block $b for chain $this: ${error.msg}"))
      case Right(b) =>
        Success(new BlockChain(blocks + (b.id -> b)))
    }
  }

  override def iterator: Iterator[Block] = blocks.valuesIterator

  override def toString: String = s"BlockChain [height: $height, score: $score]:" + iterator.mkString("\n\t", "\n\t", "\n")
}