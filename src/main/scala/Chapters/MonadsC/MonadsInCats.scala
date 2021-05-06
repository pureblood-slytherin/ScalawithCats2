package Chapters.MonadsC

import cats.syntax.applicative._
import cats.{Monad, Monoid}
import cats.instances.option._
import cats.instances.list._
import cats.instances.future._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.instances.vector._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object MonadsInCats extends App{
  val opt1 = Monad[Option].pure(3)
  val opt2 = Monad[Option].flatMap(opt1)(x=> Some(x*x))
  println(opt2)
  val listMonad = Monad[List].pure(1,2,3)
  println(listMonad)
  val list2= Monad[List].flatMap(List(1,2,3))(x=>List(x,x*10))
  println(list2)
  val list3 = Monad[List].flatMap(list2)(x=> List(x,x+23))
  println(list3)

  // Future
  val fm = Monad[Future]
  val newFM = fm.flatMap(fm.pure(2))(x=>fm.pure(x*x))
  Await.result(newFM, 1.second)
  println(newFM)

  // MONAD Syntax
  val myMonad = 1.pure[Option]
  println(myMonad)
  2.pure[List]

  def sumSquare[F[_]:Monad](a:F[Int],b:F[Int]):F[Int]=
    a.flatMap(x=>b.map(y=> (x*x)+(y*y)))
  //For comprehension
  def sumSquareFor[F[_]:Monad](a:F[Int],b:F[Int]):F[Int]= for{
    x <- a
    y <- b
  }yield (x*x+y*y)

  println(sumSquare(Option(2),Option(9)))
  println(sumSquare(Vector(1,2,3),Vector(3,4)))
  println(sumSquareFor(Vector(1,2,3),Vector(3,4)))







}
