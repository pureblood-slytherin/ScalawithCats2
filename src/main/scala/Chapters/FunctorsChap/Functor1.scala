package Chapters.FunctorsChap
//import cats.Applicative.ops.toAllApplicativeOps
import cats.instances.function._ // for Functor
import cats.syntax.functor._ // for map
object Functor1 extends App{

  val func1: Int => Double = (x: Int) => x.toDouble
  val func2: Double => Double =
    (y: Double) => y * 2

  val func3: Double => Double= _*3
  println((func1 map func2 map func3)(1)) // composition using map
  println(func3(func2(func1(1)))) // res3: Double = 2.0 // composition using map

  println((func1 andThen func2 andThen func3)(1)) // composition using andThen
  // res4: Double = 2.0 // composition using andThen func2(func1(1)) // composition written out by hand // res5: Double = 2.0

  val func =
    ((x: Int) => x.toDouble).
      map(x => x.toDouble). map(x => x * 2). map(x => x*3)
  println(func(1))


}
