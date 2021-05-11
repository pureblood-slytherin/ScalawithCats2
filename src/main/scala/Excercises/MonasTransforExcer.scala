package Excercises

import cats.data.EitherT

import scala.concurrent.{Await, Future}
import cats.syntax.applicative._
import cats.instances.future._
import cats.instances._
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object MonasTransforExcer extends App{

  type Response[A]= EitherT[Future,String,A]

  val powerLevels = Map( "Jazz" -> 6, "Bumblebee" -> 8, "Hot Rod" -> 10)

  def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
      case Some(value) => value.pure[Response]
      case None => EitherT.left(Future(s"$autobot not reachable"))
    }
  }

  println(getPowerLevel("Jazz"))

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = {
    for{
      a<- getPowerLevel(ally1)
      b<- getPowerLevel(ally2)
    }yield (a+b) >= 15
  }
  def tacticalReport(ally1: String, ally2: String): String = {
    val stack = canSpecialMove(ally1,ally2).value
    Await.result(stack,1.second) match {
      case Left(error)=> s"Comms error: $error"
      case Right(true) => s"$ally1 and $ally2 " + s"are ready to roll out"
      case Right(false)=> s"$ally1 and $ally2 " + s" need a rechanrge"
    }
  }

  println(tacticalReport("Jazz", "Bumblebee"))
  println(tacticalReport("Jazz", "Ironhide"))

}
