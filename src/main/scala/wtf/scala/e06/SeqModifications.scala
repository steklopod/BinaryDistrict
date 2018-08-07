package wtf.scala.e06

object SeqModifications {

  /**
    * Duplicate every element of seq n times
    *
    * @param n
    * @param s
    * @return
    */
  def duplicateN(n: Int, s: Seq[Int]): Seq[Int] = s.flatMap(e => Seq.fill(n)(e))


  /**
    * Remove every n-th element of seq
    *
    * @param n
    * @param s
    * @return
    */
  def removeN(n: Int, s: Seq[Int]): Seq[Int] = s.zipWithIndex.filterNot { case (_, index) =>
    index + 1 > 0 && (index + 1) % n == 0
  }.map(_._1)


  /**
    * Rotate seq by n to the left
    *
    * @param n
    * @param s
    * @return
    */
  def rotate(n: Int, s: Seq[Int]): Seq[Int] = {
    val abs = if (n < 0) s.size + n else n
    val (first, second) = s.splitAt(abs)
    second ++ first
  }

}
