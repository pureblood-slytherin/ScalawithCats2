package Chapters.CatsIntro
import java.util.Date
import cats._
import cats.implicits.toShow


object CustomInstance extends App{


  implicit val dateShow : Show[Date] =
    new Show[Date]{
      override def show(t: Date): String = s"${t.getTime}ms since the beginning"
    }


  println(new Date().show)
}
