package CaeStudies
import cats.{Foldable, Monoid}
import cats.syntax.monoid._
import cats.instances.int._
import cats.instances.bigInt._
import cats.instances.string._
import cats.instances.vector._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

import cats.syntax.foldable._
import cats.syntax.traverse._
import cats.instances.future._

object MapReduce extends App{
  def foldMap[A,B: Monoid](vector :Vector[A])(fn: A=>B):B ={
    vector.map(fn).foldLeft(Monoid.empty[B])(_ |+| _)
  }
  val add = (x:Int,y:Int) => x + y
  val f = (i: Int) => i*2
  println(foldMap(Vector(1, 2, 3, 4, 6))(f))
  println(foldMap(Vector("Welcome ", "to ", " Hogwarts!"))(_.toUpperCase))

  //query the number of available CPUs on our machine
  println(Runtime.getRuntime.availableProcessors)

  // Dividing the List to smaller list
  println((1 to 15).toList.grouped(4).toList)


  def parallelFoldMap[A,B: Monoid](vec: Vector[A])(fn: A=>B):Future[B]= {
    val numCores = Runtime.getRuntime.availableProcessors
    val groupSize = (1.0 * vec.size / numCores).ceil.toInt

    // Create one group for each CPU:
    val groups: Iterator[Vector[A]] = vec.grouped(groupSize)

    // Create a future to foldMap each group:
    val future: Iterator[Future[B]]= groups map{group=> Future{
      group.foldLeft(Monoid[B].empty)(_ |+| fn(_))
    }}

    // foldMap over the groups to calculate a final result:
    Future.sequence(future) map {iterable =>
      iterable.foldLeft(Monoid.empty[B])(_ |+| _)
    }
  }
  val result : Future[Int]= parallelFoldMap((1 to 10000).toVector)(f)
  //println(result)

  def parallelFoldMap2[A,B:Monoid](vec: Vector[A])(fn: A=>B):Future[B] ={
    val numberCPU = Runtime.getRuntime.availableProcessors
    val grpSize = (1.0*vec.size/numberCPU).ceil.toInt

    val groups: Iterator[Vector[A]] = vec.grouped(grpSize)

    val future:Iterator[Future[B]] = groups.map(grp => Future(foldMap(grp)(fn)))

    Future.sequence(future) map{iterable =>
      iterable.foldLeft(Monoid.empty[B])(_ |+| _)
    }
  }

  val result1: Future[BigInt]= parallelFoldMap((BigInt(1) to BigInt(1000000)).toVector)(identity)
  Await.result(result1,1.second)
  println(result1)

  // Parallel fold Method with more Cats
  def parallelFoldMapCats[A,B:Monoid](vec: Vector[A])(fn: A=>B):Future[B] = {
    val numberCPU = Runtime.getRuntime.availableProcessors
    val grpSize = (1.0 * vec.size / numberCPU).ceil.toInt

    vec
      .grouped(grpSize)
      .toVector
      .traverse(grp => Future(grp.toVector.foldMap(fn)))
      .map(_.combineAll)
  }
  val result3: Future[BigInt]= parallelFoldMap((BigInt(1) to BigInt(1000000)).toVector)(identity)
  Await.result(result3,1.second)
  println(result3)
}
