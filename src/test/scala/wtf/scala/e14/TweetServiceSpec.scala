package wtf.scala.e14

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

/**
  * [[wtf.scala.e14.TweetService]] test suite
  */
class TweetServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with TweetService {

  import TweetJsonProtocol._

  def getRandomTweet(chars: Int = 140) = MockTweets.getRandomTweet(chars)

  "Tweet service" should {
    "return a list for GET /tweets" in {
      Get("/tweets") ~> route ~> check {
        entityAs[List[Tweet]]
      }
    }

    "result in 'OK' response body when posting a tweet" in {
      Post("/tweets", getRandomTweet()) ~> route ~> check {
        responseAs[String] shouldEqual "OK"
      }
    }

    "result 400 status and an error message when posting a tweet with a text having more than 140 characters" in {
      Post("/tweets", getRandomTweet(200)) ~> route ~> check {
        status shouldEqual StatusCodes.BadRequest
        responseAs[Map[String, String]] shouldEqual Map("error" -> "140 characters exceeded")
      }
    }

    "return only last 10 tweets, preserving the order they were sent in" in {
      val fifteenTweets = (0 until 15).map(getRandomTweet)
      for (tweet <- fifteenTweets) Post("/tweets", tweet) ~> route ~> check {
        responseAs[String] shouldEqual "OK"
      }

      Get("/tweets") ~> route ~> check {
        responseAs[List[Tweet]].length shouldEqual 10
        responseAs[List[Tweet]] shouldEqual fifteenTweets.takeRight(10)
      }
    }

    "return last 10 tweets by the author requested" in {
      val thirtyTweets = (0 until 30).map(getRandomTweet)
      for (tweet <- thirtyTweets) Post("/tweets", tweet) ~> route ~> check {
        responseAs[String] shouldEqual "OK"
      }

      for (author <- thirtyTweets.map(_.author).distinct) {
        Get(s"/tweets/$author") ~> route ~> check {
          for (authorTweet <- responseAs[List[Tweet]]) {
            authorTweet.author should equal(author)
          }
        }
      }
    }

    "result 404 and an error message when requesting tweets of unknown author" in {
      Get(s"/tweets/johncena") ~> route ~> check {
        status shouldEqual StatusCodes.NotFound
        responseAs[Map[String, String]] shouldEqual Map("error" -> "Unknown author")
      }
    }
  }
}
