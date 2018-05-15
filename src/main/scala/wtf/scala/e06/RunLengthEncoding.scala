package wtf.scala.e06

object RunLengthEncoding {

  /**
    * Pack consecutive duplicates of sequence elements into sublists.
    * If a list contains repeated elements they should be placed in separate sublists.
    * @param s
    * @return
    */
  def pack(s: Seq[Int]): Seq[Seq[Int]] = ???


  /**
    * Implement the so-called run-length encoding data compression method.
    * Consecutive duplicates of elements are encoded as tuples (E, N) where N is the number of duplicates of the element E.
    * @param s
    * @return
    */
  def runLengthEncode(s: Seq[Int]): Seq[(Int, Int)] = ???


  /**
    * Given a run-length code list generated with runLengthEncode, construct its uncompressed version.
    * @param encoded
    * @return
    */
  def runLengthDecode(encoded: Seq[(Int, Int)]): Seq[Int] = ???


}
