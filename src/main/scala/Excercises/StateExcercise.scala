package Excercises
import cats.data.State
import cats.syntax.applicative._ // for pure
object StateExcercise extends App{

  type CalcState[A] = State[List[Int], A]
  def evalOne(sym: String): CalcState[Int] = sym match {
    case "+" => operator(_ + _)
    case "-" => operator(_ - _)
    case "*" => operator(_ * _)
    case "/" => operator(_ / _)
    case num => operand(num.toInt)
  }

  def operand(i: Int):CalcState[Int]=
    State[List[Int],Int]{stack=>
      (i :: stack,i)
    }

  def operator(function: (Int, Int) => Int): CalcState[Int]=
    State[List[Int],Int]{
      case a::b::tail =>
       val ans = function(a,b)
        (ans::tail,ans)
      case _=> sys.error("Less operands")
    }
  def evalAll(input:List[String]):CalcState[Int] =
    input.foldLeft(0.pure[CalcState]){(a,b)=>
      a.flatMap(_=>evalOne(b))
    }

  def evalAllInput(string:String):Int =
    evalAll(string.split(" ").toList).runA(Nil).value

  println(evalAllInput("3 4 + 3 1 + *"))
}
