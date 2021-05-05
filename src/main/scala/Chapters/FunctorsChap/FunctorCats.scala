package Chapters.FunctorsChap
import cats.Functor
import cats.instances.list._
import cats.instances.vector._
import cats.instances.option._
import cats.instances.function._
import cats.syntax.functor._


object FunctorCats extends App{
  /** *
   * trait Functor[F[_]] {
   * def map[A, B](fa: F[A])(f: A => B): F[B]
   * }
   * */
  val myList = List(1,2,3,4)
  val myModifiedList = Functor[List].map(myList)(_*5)
  println(myModifiedList)
  println(myList.map(_*5))

  val myOption = Option(234)
  val myModifiedOption = Functor[Option].map(myOption)(_.toString)

  println(myModifiedOption)

  //Functor provides a method called lift, which converts a function of type A => B
  // to one that operates over a functor and has type F[A] => F[B]

  val myFun: Int=>Int = _+1
  val liftedFunc = Functor[Option].lift(myFun) //
  println(liftedFunc(Option(34)))

  // As function
  val myNewList = Functor[List].as(myList, 42)
  println(myNewList)

  //syntax
  val func1 = (a: Int) => a + 1
  val func2 = (a: Int) => a * 2
  val func3 = (a: Int) => s"${a}!"
  val func4 = func1.map(func2).map(func3)
  println(func4(123))



  //new FunctorOps(foo).map(value => value + 1)    Todo Did not understand
  // General increment
  def increment[F[_]](function: F[Int])(implicit functor: Functor[F]):F[Int]=
    function.map(_+1)

  println(increment(List(1,2,3,4)))
  println(increment(Option(23)))
  println(increment(Vector(2,3,4)))

  //The map method of FunctorOps requires an implicit Functor as a parameter.
  // This means this code will only compile if we have a Functor for F in scope
  // Example if our val is of a custom type , we need a functor of that type


}
