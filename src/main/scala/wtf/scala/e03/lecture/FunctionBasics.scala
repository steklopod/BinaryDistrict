package wtf.scala.e03.lecture

object AnonymousClass {
  trait Animal {
    def sound(): Unit
  }

  val cow = new Animal {
    def sound() = println("moo")
  }
}

object FunctionBasics extends App {


  val myFun = new Function1[Int, Int] {
    override def apply(x: Int) = x + 1
  }

  val myFun1 = (x: Int) => x + 1

  val fun0 = () => "zero parameters"
  val fun2 = (x: Int, y: Int) => x + y

  //  val myTerribleFun = (a1: Int, a2: Int, a3: Int, a4: Int, a5: Int, a6: Int, a7: Int, a8: Int, a9: Int, a10: Int, a11: Int, a12: Int, a13: Int, a14: Int, a15: Int, a16: Int, a17: Int, a18: Int, a19: Int, a20: Int, a21: Int, a22: Int, a23: Int, a24: Int, a25: Int) => "hahaha"

  def applyTwice(f: Int => Int, x: Int): Int = f(f(x))

  applyTwice(myFun, 0)

  def plusTwo(x: Int) = x + 2
  val plusTwoEta = plusTwo _

  Some(4).map(plusTwo)

}
