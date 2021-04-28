package Chapters.CatsIntro
import cats.Show
import cats.instances.int._ // for Show
import cats.instances.string._ // for Show
//import cats syntax
import cats.syntax.show._


/** import cats._          This is for importing all the type classes
 * import cats.implicits._  This is for importing all the instances
 * */

object CatsIntroduction extends App{


  val showInt: Show[Int] = Show.apply[Int]
  val showString: Show[String] = Show.apply[String]

  val intAsString: String = showInt.show(123)
  val stringAsString: String = showString.show("abc")
  println(intAsString)
  // cats syntax
  println(456.show)
  println("This is a string".show)

}
