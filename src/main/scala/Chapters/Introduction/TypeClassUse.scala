package Chapters.Introduction


import Chapters.Introduction.Introduction.{Json, JsonWriter, Person, personWriter}
import Chapters.Introduction.TypeClassUse.JsonSyntax.JsonWriterOps

object TypeClassUse extends App {

  //Interface Objects

  object Json {
    def returnsValue[A](value: A)(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }

  println(Json.returnsValue(Person("Abhijeet", 24))(personWriter))
  println(Json.returnsValue(Person("Abhijeet", 24))) // compiler will automatically dtects the implicit instance and call it by itself
  println(Json.returnsValue(7))

  // Interface Syntax
  // use extension methods to extend existing types with interface methods
  object JsonSyntax {

    implicit class JsonWriterOps[A](value: A) {
      def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
    }

  }

  println(Person("Harry", 11).toJson)
  print(7.toJson)
  // Implicitly

  def implicitly[A](implicit value: A): A =
    value

  println(implicitly[JsonWriter[Int]])
}

