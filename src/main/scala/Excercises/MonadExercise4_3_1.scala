package Excercises
//import cats.Monad
//import cats.syntax.flatMap._  //flatmap
//import cats.syntax.functor._ // Map
import cats.Id

object MonadExercise4_3_1 extends App{

  def pure[A](input: A):Id[A]=
    input

  def map[A,B](input:A)(f:A=>B):Id[B]=
    f(input)

  def flatmap[A,B](input:Id[A])(f: A=>Id[B]):Id[B]=
    f(input)


}
