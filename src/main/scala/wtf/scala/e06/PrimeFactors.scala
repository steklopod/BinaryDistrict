package wtf.scala.e06

import scala.collection.immutable.VectorBuilder

object PrimeFactors {

  /**
    * Find all prime factors of positive number and return them in ascending order
    *
    * @param num
    * @return
    */
  def primeFactors(num: Int): Seq[Int] = {
    val resultBuilder = new VectorBuilder[Int]()
    // получаем дополненное решето Эратосфена
    val sieve = sieveOfEratosthenes(num)
    // следующее временное значение получаем
    // делением предыдущего на простой делитель из решета
    var curNum = num
    while (curNum != 1) {
      resultBuilder += sieve(curNum)
      curNum /= sieve(curNum)
    }
    resultBuilder.result().sorted
  }

  def sieveOfEratosthenes(n: Int): IndexedSeq[Int] = {
    val result = new Array[Int](n + 1)
    result.update(n, n)
    for {
      i <- 2 to math.round(math.sqrt(n)).toInt
      if result(i) == 0
    } {
      result.update(i, i)
      for (j <- i * i to n by i)
        result.update(j, i)
    }
    result.toArray
  }


  /**
    * Find all prime factors of positive number and return them and their multiplicities
    * as Map of factor -> multiplicity
    *
    * @param num
    * @return
    */
  def primeFactorsMultiplicity(num: Int): Map[Int, Int] =  primeFactors(num).groupBy(identity).mapValues(_.size)

}
