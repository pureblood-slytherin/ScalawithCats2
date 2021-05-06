package Chapters.MonadsC
import cats.{Id, Monad, Monoid}
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.instances.list._
import cats.instances.int._

object IdentityMonad extends App{

  def sumOfSquare[F[_]:Monad](a: F[Int],b:F[Int]):F[Int]= for{
    x<- a
    y<- b
  }yield x*x+y*y

  val mysQuare = sumOfSquare(List(2),List(3))
  println(mysQuare)
  //println(sumOfSquare(1,2)) // This is not possible
  //Instead
  val mysingleSquareSum = sumOfSquare(3:Id[Int],4:Id[Int])
  println(mysingleSquareSum)
  val myIdList:Id[List[Int]]= List(1,2,3)   // this is valid as Id type gives the type that is inside it
  val myIdString: Id[String] = "Harry"
  val myString = "Harry"
  // Definition of Id is
  //  type Id[A] = A
  // Using Id we can call map , and flatmap on single value

  val a= Monad[Id].pure(3)
  val b= Monad[Id].flatMap(a)(_*3)
  val sum = for{
    x<-a
    y<-b
  } yield x+y
  println(sum)

}
