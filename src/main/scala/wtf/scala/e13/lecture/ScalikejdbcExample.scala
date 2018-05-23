package wtf.scala.e13.lecture

import scalikejdbc.{ConnectionPool, _}

import scala.concurrent.Future

object ScalikejdbcExample extends App {

  object Documents extends SQLSyntaxSupport[Document] {
    override val tableName = "documents"

    override val columnNames = Seq("owner_id", "content")

    def apply(rs: WrappedResultSet) = Document(
      rs.long("judge_id"), rs.string("content"))

    def create(ownerId: Long, content: String)(implicit session: DBSession = autoSession): Future[Boolean] = {
      val sql = withSQL(insert.into(Documents).namedValues(
        column.ownerId -> ownerId,
        column.content -> content))
      Future {
        sql.update().apply() == 1
      }
    }
  }

  object Judges extends SQLSyntaxSupport[Judge] {
    override val tableName = "judges"

    def apply(rs: WrappedResultSet) = Judge(
      rs.string("name"), rs.stringOpt("specialization"))
  }

  // initialize JDBC driver & connection pool
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:example;DB_CLOSE_DELAY=-1", "", "")

  implicit val session = AutoSession

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
  """.execute.apply()

  // insert initial data
  Seq(
    ("Alice", Some("lawyer")),
    ("Bob", None),
    ("Chris", Some("property"))) foreach { case (name, property) =>
    sql"insert into judges (name, specialization) values ($name, $property)".update.apply()
  }

  Seq("aaa", "bbb", "ccc").zipWithIndex foreach { case (content, i) =>
    Documents.create(i + 1, content)
  }

  val judgesEntities: List[Map[String, Any]] = sql"select * from judges".map(_.toMap).list.apply()
  println(judgesEntities)

  // find all members
  val judges: List[Judge] = sql"select * from judges".map(rs => Judges(rs)).list.apply()
  println(judges)

  // find all documents
  val documentsEntities: List[Map[String, Any]] = sql"select * from documents".map(_.toMap).list.apply()
  println(documentsEntities)

  val j = Judges.syntax("j")
  val name = "Alice"
  val alice: Option[Judge] = withSQL {
    select.from(Judges as j).where.eq(j.name, name)
  }.map(rs => Judges(rs)).single.apply()
  println(alice)
}
