package wtf.scala.e13.lecture

import com.github.fakemongo.Fongo
import com.mongodb.casbah.Imports._
import com.mongodb.{BasicDBList, DBObject}
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject

import scala.collection.JavaConverters._

case class Entity(_id: String, params: Param)
case class Param(x: Int, y: Seq[Int])

object CasbahExamples extends App {

  val host = "localhost:27017"
  val db = "big_cash"
  val collection = "test"

//  val mongoClient: MongoClient = MongoClient(host)
//  val dbCLient: MongoDB = MongoClient(host)(db)
//  val collectionClient: MongoCollection = MongoClient(host)(db)(collection)

  val fongo = new Fongo("test")
  val collectionClient = new MongoCollection(fongo.getDB(db).getCollection(collection))

  collectionClient.findOneByID("333").map { o =>
    val id = o.get("_id").toString
    val params = o.get("params").asInstanceOf[DBObject]
    val x = params.as[Double]("x").toInt
    val y = params.get("y").asInstanceOf[BasicDBList].iterator().asScala.toList.map(_.toString.toDouble.toInt)
    Entity(id, Param(x, y))
  }

  val cursor = collectionClient.find(MongoDBObject("params.x" -> 15))

  val ids = cursor.map { o =>
    o.get("_id").toString
  }.toList

  cursor.close()

  val rangeCursor = collectionClient.find("params.x" $gt 14 $lt 16)

  val rangeIds = rangeCursor.map { o =>
    o.get("_id").toString
  }.toList

  rangeCursor.close()

  val writeResult: WriteResult = collectionClient.insert(MongoDBObject("_id" -> "0011", "params" -> MongoDBObject("x" -> 1, "y" -> List(0, 0, 1))))
  writeResult.getN
  writeResult.isUpdateOfExisting
  writeResult.getUpsertedId

  val removedDoc: Option[DBObject] = collectionClient.findAndRemove(MongoDBObject("_id" -> "001"))
  print(removedDoc)


  val removeResult: WriteResult = collectionClient.remove(MongoDBObject("_id" -> "0011", "params" -> MongoDBObject("x" -> 1, "y" -> List(0, 0, 1))))

  val updateResult: WriteResult = collectionClient.update(MongoDBObject("_id" -> "0011"),
    $set("params.x" -> 1),
    upsert = false,
    multi = true)

  val saveResult: WriteResult = collectionClient.save(MongoDBObject("_id" -> "9", "params" -> MongoDBObject("x" -> 1, "y" -> List(0, 0, 1))))

}
