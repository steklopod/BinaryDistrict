package wtf.scala.e08.lecture

import scala.io.Source
import spray.json._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object LoadAndParseJson extends App with DefaultJsonProtocol {
  case class Ticker(`15m`: Float, last: Float, buy: Float, sell: Float, symbol: String)
  implicit val tickerProtocol: JsonFormat[Ticker] = jsonFormat5(Ticker.apply)
  val jsonF = Future(Source.fromURL("https://blockchain.info/ticker?cors=true").getLines().mkString(""))
  val parsedF = jsonF.map(json =>
    json.parseJson.convertTo[Map[String, Ticker]])
  println(Await.result(parsedF, 1 minute))
}