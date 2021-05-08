package Chapters.MonadsC

import cats.data.Writer
import cats.syntax.applicative._
import cats.instances.vector._
import cats.syntax.writer._

object WriterMonad extends App{

  val myWriter = Writer(Vector("Hello","Hi"),23)
  println(myWriter)

  type myNewWriter[A] = Writer[Vector[String],A]
  val mymadeUpWriter= 23.pure[myNewWriter]
  println(mymadeUpWriter)
  val tellWriter = Vector("I'll tell writer").tell
  println(tellWriter)
  val aNewWriter = 42.writer(Vector("The answer of the ultimate Question is "))
  println(aNewWriter)
  // Extracting results
  //value   written run
  val myResult = aNewWriter.value
  val myStringResult = aNewWriter.written
  val (string,int)= aNewWriter.run
  println(myResult  )
  println(myStringResult)
  println(int)
  println(string)
  // Composing and transforming a Writer
  val writer1 = for {
    a <- 10.pure[myNewWriter]
    _ <- Vector("a", "b", "c").tell
    b <- 32.writer(Vector("x", "y", "z"))
  } yield a + b
  println(writer1)

  val writer2 = writer1.mapWritten(_.map(_.toUpperCase))
  println(writer2)

  val writer3 = writer1.bimap(
    log => log.map(_.toUpperCase),
    res => res * 100
  )
  println(writer3)

  val writer4 = writer1.mapBoth { (log, res) =>
    val log2 = log.map(_ + "!")
    val res2 = res * 1000
    (log2, res2)
  }
  println(writer4)
  // Clear the log
  val writer5 = writer1.reset
  println(writer5)

  val writer6 = writer1.swap
  println(writer6)



}
