package wtf.scala.e02.lecture

object PatternMatchingExamples {
  def matchInt(num: Int): String = {
    num match {
      case 1 => "one"
      case 2 => "two"
      case _ => "many"
    }
  }

  def matchIntCondition(num: Int): String = {
    num match {
      case i if i == 1 => "one"
      case i if i == 2 | i == 3 => "two or three"
      case _ => "many"
    }
  }

  def matchType(obj: Any): String = {
    obj match {
      case 1 => "one"
      case "two" => "two"
      case y: Int => "many"
      case z => s"other: $z"
    }
  }

  def matchNotExhaustive(num: Int): String = {
    num match {
      case 1 => "one"
      case 2 => "two"
    }
  }

  // Синтаксический сахар
  val tuple = (1, "Scala", 2.12)
  val (a, b, c): (Int, String, Double) = (1, "Scala", 2.12)
  val first = tuple._1 // 1

  object Domain {
    def unapplySeq(domain: String): Option[Seq[String]] = Some(domain.split("\\."))
  }
  object DomainExample extends App {
    "google.com" match {
      case Domain("google", "com") => println("Hey, Google!") // matches
      case Domain("yahoo", _*) => println("yahoo")
      case _ => println("other")
    }
  }

  object Even {
    def unapply(num: Int): Boolean = num % 2 == 0
  }

  object EvenExample extends App {
    4 match {
      case e @ Even() => println(s"$e is even") // 4 is even
      case e @_ => println  (s"$e is odd")
    }
  }

  object CaseClassMatching extends App {
    val opt: Option[Int] = Some(4)
    opt match {
      case Some(n) if n > 5 => println(s"My number $n > 5")
      case Some(_) => println("My number is <5") // matched
      case None =>  println("No number")
    }
  }

  object Twice {
    def unapply(num: Int): Option[Int] = if (num % 2 == 0) Some(num/2) else None
  }

  object TwiceExample extends App {
    val x = 34
    x match {
      case Twice(n) => println(s"Twice $n") // Twice 17
      case _ => println("Not twice")
    }
  }

}
