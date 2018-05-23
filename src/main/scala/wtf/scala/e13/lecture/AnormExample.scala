package wtf.scala.e13.lecture

import java.sql.Connection

import anorm._
import scalikejdbc.ConnectionPool

object AnormExample extends App {
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:example;DB_CLOSE_DELAY=-1", "", "")

  implicit val conn: Connection = ConnectionPool.borrow()

  SQL(
    """create table judges (
    id serial not null primary key,
    name varchar(64) not null,
    specialization varchar(64)
  );
  create table documents (
    owner_id varchar(64) not null,
    content varchar(64) not null
  );""").execute()
  // insert initial data
  Seq(
    ("Alice", Some("lawyer")),
    ("Bob", None),
    ("Chris", Some("property"))) foreach { case (name, property) =>
    SQL"insert into judges (name, specialization) values ($name, $property)".executeUpdate()
  }

  Seq("aaa", "bbb", "ccc").zipWithIndex foreach { case (content, i) =>
    SQL"insert into documents (owner_id, content) values (${i + 1}, $content)".executeUpdate()
  }

  val parser: RowParser[Map[String, Any]] =
    SqlParser.folder(Map.empty[String, Any]) { (map, value, meta) =>
      Right(map + (meta.column.qualified -> value))
    }
  val selectAll: SqlQuery = SQL("select * from judges")

  val allJudges: List[Map[String, Any]] = selectAll.as(parser.*)

  println(allJudges)

  val judge: RowParser[Judge] = Macro.parser[Judge]("name", "specialization")

  println(selectAll.as(judge.*))
}
