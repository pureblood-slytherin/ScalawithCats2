package Chapters.FoldableandTraverse
import cats.Applicative
import cats.instances.future._
import cats.syntax.applicative._
import cats.instances.vector._
import cats.instances.option._
import cats.data.Validated
import cats.instances.list._

import scala.concurrent.{Await, Future, duration}
import scala.concurrent.ExecutionContext.Implicits.global
import cats.syntax.apply._

import scala.concurrent.duration.DurationInt

object TraverseApplicative extends App{
  val hostname = List("abc.com","fb.com","aws.amazon.com")
  println(Future(List.empty[Int]))
  println(List.empty[Int].pure[Future])

  def getUptime(hostname: String): Future[Int] = Future(hostname.length * 60) // just for demonstration

  def newCombine(accum: Future[List[Int]],host: String):Future[List[Int]]=
    (accum,getUptime(host)).mapN(_:+_)

  // Definition of list Traverse & list Sequence is generic and can be used for any type
  def listTraverse[F[_]: Applicative, A, B]
  (list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) => (accum, func(item)).mapN(_ :+ _)
    }
  def listSequence[F[_]: Applicative, B] (list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)


  val totalUptime = listTraverse(hostname)(getUptime)
  println(Await.result(totalUptime, 1.second))

  // Traverse excercise
  println(listSequence(List(Vector(1, 2), Vector(3, 4))))

  println(listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6))))

  def process(inputs: List[Int]) =
    listTraverse(inputs)(n => if(n % 2 == 0) Some(n) else None)

  println(process(List(2, 6)))
  println(process(List(2,6,1)))
  // Traverse in validated
  type ErrorsOr[A] = Validated[List[String], A]

  def processWValidated(inputs: List[Int]): ErrorsOr[List[Int]] = listTraverse(inputs) { n =>
    if(n % 2 == 0) { Validated.valid(n)
    } else {
      Validated.invalid(List(s"$n is not even"))
    } }

  println(processWValidated(List(2, 4, 6)))
  println(processWValidated(List(1, 2, 3)))


}
