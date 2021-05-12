package Chapters.SemigropalApplicative
import cats.Semigroupal
import scala.concurrent.Future
import cats.instances.future._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.list._
import cats.instances.either._
import cats.syntax.parallel._
import cats.instances.vector._


object SemigropalsType extends App{
  val myFutureGroup = Semigroupal[Future].product(Future("Hello"),Future(3))
  println(Await.result(myFutureGroup, 1.second))

  import cats.syntax.apply._ // for mapN
  case class Cat(
                  name: String,
                  yearOfBirth: Int,
                  favoriteFoods: List[String]
                )
  val futureCat = (
    Future("Garfield"),
    Future(1978),
    Future(List("Lasagne"))
    ).mapN(Cat.apply)
  println(Await.result(futureCat, 1.second))

  // List


  println(Semigroupal[List].product(List(1, 2), List(3, 4)))

  type ErrorOr[A]= Either[Vector[String],A]
  println(Semigroupal[ErrorOr].product(
    Left(Vector("Error 1")), Left(Vector("one"))
  ))
  val error1: ErrorOr[Int] = Left(Vector("Error 1"))
  val error2: ErrorOr[Int] = Left(Vector("Error 2"))

  Semigroupal[ErrorOr].product(error1, error2)
  println((error1, error2).tupled)
  //To collect all the errors we simply replace tupled with its “parallel” version called parTupled.
  println((error1, error2).parTupled)

  // parMapN
  val sucess1:ErrorOr[Int] = Right(1)
  val sucess2:ErrorOr[Int] = Right(3)
  val addTwo= (x:Int,y:Int) => x + y
  println("Line break")
  println((error1, error2).parMapN(addTwo))
  println((sucess1, sucess2).parMapN(addTwo))





}
