package Chapters.CatsIntro

import cats.Eq
import cats.implicits.catsSyntaxEq
import cats.syntax.eq._
import java.util.Date
import cats.instances.long._

object CompareCustomTypes extends App{
  val y = new Date()

  implicit val dateEquality :Eq[Date] =
    Eq.instance[Date] {
      (date1: Date, date2: Date) => date1.getTime === date2.getTime
    }

  val x = new Date()

  println(x===x)
  println(x===y)
  //println(x.getTime + "  "+  y.getTime)

}
