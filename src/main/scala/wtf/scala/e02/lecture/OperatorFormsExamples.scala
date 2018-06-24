package wtf.scala.e02.lecture

object OperatorFormsExamples {
  case class Unary(num: Int) {
    def unary_+ = Unary(num + 1)
    def unary_-() = Unary(num - 1)
    def unary_!() = Unary(num * 2)
    def unary_~() = Unary(num * num)
  }

  object UnaryTest {
    val plus = +Unary(3) // Unary(4)
    val minus = -Unary(3) // Unary(2)
    val bang = !Unary(3) // Unary(6)
    val tilde = ~Unary(3) // Unary(9)

    // не рекомендуется
    val badPlus = Unary(3).unary_+
  }

  case class Infix(num: Int) {
    def +   (that: Infix) = Infix(this.num + that.num)
    def add (that: Infix) = Infix(this.num + that.num)

    def *   (that: Infix) = Infix(this.num * that.num)
    def mult(that: Infix) = Infix(this.num * that.num)
  }

  object Infix {
    val a = Infix(1) add Infix(2) mult Infix(3) // Infix(9)
    val b = Infix(1)  +  Infix(2)  *   Infix(3) // Infix(7)
  }

  case class InfixRight(num: Int) {
    def ++(that: InfixRight) = InfixRight(this.num + this.num * that.num)
    def +:(that: InfixRight) = InfixRight(this.num + this.num * that.num)
  }

  object InfixRight {
    val a = InfixRight(0) ++ InfixRight(1) ++ InfixRight(2) // InfixRight(0)
    //     (InfixRight(0) ++ InfixRight(1)) ++ InfixRight(2)
    val b = InfixRight(0) +: InfixRight(1) +: InfixRight(2) // InfixRight(4)
    //     (InfixRight(0) +: (InfixRight(1) +: InfixRight(2))
    //     (InfixRight(2) ++ InfixRight(1)) ++ InfixRight(0)
  }

  val str = "hello"
  str.endsWith("o")
  str endsWith "o"

  val trimmed = "1234 ".trim
  val trimmedP = "1234 " trim
}
