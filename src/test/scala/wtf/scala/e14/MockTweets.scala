package wtf.scala.e14

import scala.util.Random

/**
  * Here we generate random tweets with their texts being random sub-140 chars subsets of Lorem ipsum text.
  */
object MockTweets {

  val names = List("frank", "bob", "mary", "alice", "osama")

  val LoremIpsumText: List[String] =
    """
      |Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
      |magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
      |consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
      |Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
    """.stripMargin.split(" ").toList

  private def getRandomText(maxChars: Int): String = {
    Random.shuffle(LoremIpsumText).reduce { (text, nextWord) =>
      if (text.length + nextWord.length + 1 > maxChars) {
        text
      } else {
        text + " " + nextWord
      }
    }
  }

  def getRandomTweet(maxChars: Int) = Tweet(
    author = names(Random.nextInt(names.size)),
    text   = getRandomText(maxChars)
  )

}
