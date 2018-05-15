package wtf.scala.e08.lecture

import spray.json.{DefaultJsonProtocol, _}

object SprayJsonExample extends App {


  case class ServerMessage(stack: Option[List[Int]] = None,
                           currentOffer: Option[Offer] = None,
                           moneyWon: Option[Int] = None,
                           isError: Boolean = false)

  case class Offer(banknotes: List[Int],
                   offerNumber: Int)

  object ServerMessageJsonProtocol extends DefaultJsonProtocol {
    implicit val offerJsonProtocol = jsonFormat2(Offer.apply)
    implicit val serverMessageJsonProtocol = jsonFormat4(ServerMessage.apply)
  }

  import ServerMessageJsonProtocol._

  val offer = Offer(List(1, 2), 4)
  val msg = ServerMessage(Some(List(4, 5)), Some(offer), None, false)

  val json = msg.toJson.toString

  println(json)

  val jsonStr =
    """{
      |"stack":[4,5],
      |"currentOffer":{"banknotes":[1,2],"offerNumber":4},
      |"isError":false}""".stripMargin
  val deserialized = json.parseJson.convertTo[ServerMessage]
  println(deserialized)
}
