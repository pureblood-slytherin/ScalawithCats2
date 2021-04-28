package Chapters.CatsIntro
import cats._
import cats.instances.int._ // for Show
import cats.instances.string._ // for Show

object CatsIntroduction extends App{


  val showInt: Show[Int] = Show.apply[Int]
  val showString: Show[String] = Show.apply[String]

  val intAsString: String = showInt.show(123)
  val stringAsString: String = showString.show("abc")
  println(intAsString)
}
