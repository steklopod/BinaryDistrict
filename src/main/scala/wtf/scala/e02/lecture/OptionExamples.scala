package wtf.scala.e02.lecture

object OptionExamples {

  val someMessage: Option[String] = Some("hello!")
  val noneMessage: Option[String] = None

  val existingMessage: Option[String] = Option("Hi") // Some("hi")
  val absentMessage: Option[String] = Option(null) // None



  object OptionForComprehensions {
    val nameOpt = Some("Ivan")
    val lastNameOpt = Some("Petrov")

    val x = for {
      name <- nameOpt
      lastName <- lastNameOpt
    } yield name + lastName // Some("IvanPetrov")

    val y = nameOpt.flatMap {
      name => lastNameOpt.map {
        lastName => name + lastName
      }
    } // Some("IvanPetrov")
  }


}









