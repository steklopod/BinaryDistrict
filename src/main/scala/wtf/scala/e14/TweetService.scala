package wtf.scala.e14

import java.util.concurrent.ConcurrentLinkedDeque

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import spray.json._

case class Tweet(author: String, text: String)

object TweetJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val locationFormat: RootJsonFormat[Tweet] = jsonFormat2(Tweet)
}


/**
  * Let's build a simple Twitter-like service REST API using Akka HTTP.
  *
  * We need the following routes:
  *
  * - GET /tweets must return last 10 tweets submitted
  *
  * - POST /tweets must accept <code>{"author": "sam", "text": "Hello world"}</code> JSON and store it in memory. If
  * a text is exceeding 140 characters, {"error": "140 characters exceeded"} message must be returned with 400 status
  *
  * - GET /tweets/:author must return the last 10 tweets by the author requested. If no tweets by the author requested
  * exist, return 404 with a <code>{"error": "Unknown author"}</code> message
  *
  */
trait TweetService {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val list = new ConcurrentLinkedDeque[Tweet]()

  import TweetJsonProtocol._
  val route: Route = ???

}
