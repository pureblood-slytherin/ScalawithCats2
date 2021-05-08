package Excercises
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import cats.data.Writer
import cats.syntax.applicative._
import cats.instances.vector._
import cats.syntax.writer._


object Exercise4_7_3 extends App{
  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  type Logged[A] = Writer[Vector[String],A]
  // Simple Factorail
  def factorial(n: Int): Int = {
    val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }
  // Factorial with Writer
  def WriterFactorial(n:Int):Logged[Int]={
    for{
      ans<- if(n==0){
            1.pure[Logged]
          }else{
            slowly(WriterFactorial(n - 1).map(_ * n))
          }
      _ <- Vector(s"fact $n $ans").tell
    }yield ans

  }
  //val Answer = factorial(5)
  //println(Answer)

  val Answer2 = Await.result(Future.sequence(Vector(
    Future(factorial(5)),
    Future(factorial(9))
  )), 5.seconds)

  println(Answer2)

  val Answer3 = Await.result(Future.sequence(Vector(
    Future(WriterFactorial(5)),
    Future(WriterFactorial(9))
  )), 5.seconds)

  println(Answer3)
}
