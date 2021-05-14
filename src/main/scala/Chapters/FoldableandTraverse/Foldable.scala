package Chapters.FoldableandTraverse
import cats.{Eval, Foldable}
import cats.instances.list._
import cats.instances.option._
import cats.instances.int._
import cats.instances.string._
import cats.instances.vector._
import cats.syntax.foldable._


object Foldables extends App{

  def show[A](list: List[A]): String =
    list.foldLeft("nil")((accum, item) => s"$item then $accum")

  println(show(Nil))
  println(show(List("I", "am", "Groot")))

  val myList = List(2,3,4)
  println(Foldable[List].foldLeft(myList, 0)(_ + _))

  val myOptionInt = Option(42)
  println(Foldable[Option].foldLeft(myOptionInt, 10)(_ + _))

  // Foldable on Eval
  def bigData = (1 to 100000).to(LazyList)
  //bigData.foldRight(0L)(_ + _)   THIS WILL GIVE STACKOVER FLOW

  import cats.instances.lazyList._

  val myEval: Eval[Long]=
    Foldable[LazyList].foldRight(bigData,Eval.now(0L))((num,eval)=> eval.map(_+num))

  println(myEval.value)
  // Folding with Monoids
  println(Foldable[Option].nonEmpty(None))
  println(Foldable[List].find(List(1, 6, 3, 8))(_ % 2 == 0))
  // Combine all
  println(Foldable[List].combineAll(List(1, 2, 3)))
  println(Foldable[List].combineAll(List("I ","am ","Groot")))
  println(Foldable[List].foldMap(List(1, 2, 3))(_.toString))

  // Composing
  val ints = List(Vector(1, 2, 3), Vector(4, 5, 6))
  println((Foldable[List] compose Foldable[Vector]).combineAll(ints))

  // Syntax for foldable
  println(List(2, 3, 5, 6, 7).combineAll)
  println(List(1, 2, 3, 4).foldMap(_.toString))

}
