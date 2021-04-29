package Chapters.MonoidsSemigroups
import cats.Monoid
import cats.Semigroup
import cats.instances.string._
import cats.instances.int._
import cats.instances.option._
import cats.syntax.semigroup._

object MonoidWithCats extends App{
  val myString = Monoid[String].combine("Welcome"," to Hogwarts")
  val myOtherString = Monoid.apply[String].combine("Welcome"," to Hogwarts !!")
  println(myString)

  val myEmptyString = Monoid[String].empty
  println(myEmptyString)
  println(myOtherString)

  // Monoid are extension of semigroup, if we don't need empty we can use directly semigroups
  val mySemigroupCombie = Semigroup[String].combine("I didn't used"," Monoid for this!")
  println(mySemigroupCombie)

  val myInt = Monoid[Int].combine(23,56)
  println(myInt)

  val myOption = Monoid[Option[String]].combine(Some("Hi"),Option(""))
  println(myOption)

  //Syntax in Monoids
  // import first
  val mySyntaxString = "You" |+| " know" |+| " who" |+| Monoid[String].empty
  println(mySyntaxString)

  val mySyntaxInt = 12 |+| 56 |+| 46 |+| Monoid[Int].empty
  println(mySyntaxInt)



}
