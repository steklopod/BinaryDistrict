package wtf.scala.e13.lecture

import com.github.fakemongo.Fongo
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import salat.dao.{SalatDAO, SalatMongoCursor}
import salat.global._

object SalatExamples extends App {

  val fongo = new Fongo("jff")

  class EntityDAO extends SalatDAO[Entity, String](collection = new MongoCollection(fongo.getDB("test").getCollection("test")))

  val dao = new EntityDAO

  val entity: Option[Entity] = dao.findOneById("333")

  val queryResult: SalatMongoCursor[Entity] = dao.find(MongoDBObject("params.x" -> 15))

  val res: List[Entity] = queryResult.toList
  queryResult.close()

  val maybeId: Option[String] = dao.insert(Entity("4", Param(4, List(4, 5, 6))))

  val ids: List[Option[String]] = dao.insert(List(Entity("5", Param(5, List(1))),
    Entity("6", Param(6, List.empty))))

  val saveResult: WriteResult = dao.save(Entity("5", Param(1, List(1))))

  val removeByIdResult = dao.removeById("4")

  val removeByQueryResult = dao.remove(MongoDBObject("_id" -> "5"))

  val removeEntityResult = dao.remove(Entity("6", Param(6, List.empty)))

  val updateResult: WriteResult = dao.update(MongoDBObject("_id" -> "4"), $set("params.x" -> 18), upsert = false)

}
