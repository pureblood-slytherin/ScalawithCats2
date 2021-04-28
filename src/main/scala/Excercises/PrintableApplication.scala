package Excercises
import PrintableDog._
import printableSyntax._

object PrintableApplication extends App{
  val myDog = Dog("Rambo",10,"Brown")

  printable.autoprintMethod(myDog)
  // syntax method
  myDog.autoprintMethod

}