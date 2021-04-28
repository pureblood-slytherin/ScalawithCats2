package Chapters.Introduction

import Chapters.Introduction.Introduction.JsonWriterInstances.stringWriter
import Chapters.Introduction.Introduction.{JsNull, JsonWriter, Person}
import Chapters.Introduction.TypeClassUse.Json

object WorkingwithImplicit extends App {


  //Recursive Implicit Resolution
  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]]=
    new JsonWriter[Option[A]] {
      override def write(option: Option[A]): Introduction.Json =
        option match{
          case Some(avalue) => writer.write(avalue)
          case None => JsNull
        }
    }

  println(Json.returnsValue(Option("A string")))

  //implicit resolution becomes a search through the space of possible combinations of implicit definitions,
  // to find a combination that creates a type class instance of the correct overall type.
}

