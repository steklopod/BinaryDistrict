package wtf.scala.e06

object MapModifications {

  /**
    * Construct new map where keys are pair of (key, value) from original map
    * and values are keys of original map taken to the power of corresponding values
    * @param m
    * @return
    */
  def power(m: Map[Int, Int]): Map[(Int, Int), Int] = m.map(p => p -> math.pow(p._1, p._2).toInt)

  /**
    * Construct new map where keys are unique values from original map
    * and values are all the keys from original map mapped to that value
    * @param m
    * @return
    */
  def revert(m: Map[Int, Int]): Map[Int, Set[Int]] = m.map {
    case (k, v) => v -> m.filter(_._2 == v).keySet
  }

}
