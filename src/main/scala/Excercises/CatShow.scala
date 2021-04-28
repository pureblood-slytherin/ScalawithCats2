package Excercises
import cats._
import cats.syntax
import cats.implicits._

object CatShow extends App{

  val Scamper = new Dog("Scamper",12,"White")
  implicit val DogShow = new Show[Dog] {
    override def show(t: Dog): String = s"${t.name} is a ${t.age} years ${t.color} Dog"
  }
  println(Scamper.show)

}
