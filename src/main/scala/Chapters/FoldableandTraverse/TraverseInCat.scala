package Chapters.FoldableandTraverse
import cats.Traverse
import cats.instances.list._
import cats.instances.future._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import cats.syntax.traverse._

object TraverseInCat extends App{
  val hostName = List("Abc.com","fb.com","amazon.in")
  val totalUpTime: Future[List[Int]]=
    Traverse[List].traverse(hostName)(a=> Future(a.length*60))

  println(Await.result(totalUpTime, 1.second))

  val numbers = List(Future(1), Future(2), Future(3))
  val numbers2: Future[List[Int]] =
    Traverse[List].sequence(numbers)

  println(Await.result(numbers2,1.second))

  // syntax version of traverse

  println(Await.result(hostName.traverse(a=> Future(a.length*60)),1.second))
  //println(Await.result(numbers.sequence, 1.second))



}
