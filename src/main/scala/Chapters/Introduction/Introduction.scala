package Chapters.Introduction

object Introduction {

  // Define a simple JSON AST, TODO 1. What is JSON AST ?  AST: Abstract simple tree
  sealed trait Json

  // The "Serialize to JSON" behaviour is encoded in this trait
  trait JsonWriter[A] {
    def write(value: A): Json
  }

  final case class Jsobject(get: Map[String, Json]) extends Json

  final case class JsString(get: String) extends Json

  final case class JsNumber(get: Int) extends Json

  final case object JsNull extends Json

  //Type class instances
  final case class Person(name: String, age: Int)

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] = {
      new JsonWriter[String] {
        override def write(value: String): Json = JsString(value)
      }
    }
  }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      override def write(value: Person): Json = Jsobject(Map(
        "Name" -> JsString(value.name),
        "Age" -> JsNumber(value.age)
      ))
    }
  implicit val NumberWriter: JsonWriter[Int] =
    new JsonWriter[Int] {
      override def write(value: Int): Json = JsNumber(value)
    }

  // Type class use


}

