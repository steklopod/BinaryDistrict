package wtf.scala.e13.lecture

import cats.effect.IO
import doobie.imports._

object DoobieExample extends App {
  val xa = Transactor.fromDriverManager[IO](
    "org.h2.Driver", "jdbc:h2:mem:example;DB_CLOSE_DELAY=-1", "", ""
  )
  // table creation
  sql"""
      create table judges (
        id serial not null primary key,
        name varchar(64) not null,
        specialization varchar(64)
      );
      create table documents (
        owner_id varchar(64) not null,
        content varchar(64) not null
      );
  """.update.run.transact(xa).attempt

  // insert initial data
  Seq(
    ("Alice", Some("lawyer")),
    ("Bob", None),
    ("Chris", Some("property"))) foreach { case (name, property) =>
    sql"insert into judges (name, specialization) values ($name, $property)".update.run.transact(xa).attempt
  }

  Seq("aaa", "bbb", "ccc").zipWithIndex foreach { case (content, i) =>
    sql"insert into documents (owner_id, content) values (${i + 1}, $content)".update.run.transact(xa).attempt
  }

  val judges =
    sql"select * from judges"
      .query[Judge]
      .to[List]
      .transact(xa)
      .attempt

  println(judges)
}
