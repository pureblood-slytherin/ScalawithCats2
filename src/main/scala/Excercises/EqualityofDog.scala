package Excercises
import cats.Eq
import cats.instances.string._
import cats.instances.int._
import cats.instances.option._
import cats.syntax.eq._

object EqualityofDog extends App{
  val Shiro = new Dog("Shiro",5,"White")
  val Shiro_clone = new Dog("Shiro",5,"White")

  implicit val DogEquality:Eq[Dog] =
    Eq.instance[Dog] {
      (Dog1,Dog2)=> Dog1.name === Dog2.name && Dog1.color===Dog2.color && Dog1.age===Dog2.age
    }

  println(Shiro===Shiro_clone)

  // Some === None check

  val optionDog = Option(Shiro)
  val emptyDog = Option.empty[Dog]

  println(optionDog===emptyDog)  // import instances.option
}
