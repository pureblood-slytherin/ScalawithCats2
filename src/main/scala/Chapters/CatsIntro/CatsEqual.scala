package Chapters.CatsIntro
import cats.Eq
import cats.data.Chain.Empty
import cats.data.OptionT.some
import cats.instances.int._
import cats.syntax.eq._
import cats.instances.option._
import cats.syntax.option._

object CatsEqual extends App {

  val eqInt = Eq[Int]
  println(eqInt.eqv(100,100))
  println(eqInt.eqv(100,101))

  // eqInt.eqv(123, "234")  type mismatch;

  println(100===100)
  println(100=!=100)
  //println(123==="123") error: type mismatch;

  println((Some(1) : Option[Int]) === (None : Option[Int]))
  println(Option(1) === Option.empty[Int] )
  // ussing syntax
  //some === none[Int]











}
