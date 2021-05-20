package CaeStudies

import cats.Semigroup
import cats.syntax.either._
import cats.instances.list._
import cats.syntax.semigroup._

object DataValidation extends App{

  val semigroup = Semigroup[List[String]]
  println(List("1st Error") |+| List("2nd Error"))

  trait Check[E, A] {
    def and(that: Check[E, A]): Check[E, A]
  }

  final case class CheckF[E,A](func: A=> Either[E,A]){
    def apply(a:A):Either[E,A]= func(a)

    def and(that: CheckF[E,A])(implicit s:Semigroup[E]):CheckF[E,A]=
      CheckF{a=>(this(a),that(a)) match{
        case (Left(e1),Left(e2))=> (e1 |+| e2).asLeft
        case ((Left(e)),Right(_)) => e.asLeft
        case (Right(_),Left(e)) => e.asLeft
        case (Right(_),Right(_)) => a.asRight
      }
      }
  }
  def lesserTwo(a:Int):Either[List[String],Int]=
    if(a<2) a.asRight
    else List("Must be less than 2").asLeft

  def greaterTwo(a:Int):Either[List[String],Int]=
    if(a> -2) a.asRight
    else List("Must be greater than -2").asLeft

  val a : CheckF[List[String],Int] =
    CheckF(greaterTwo)

  val b : CheckF[List[String],Int] =
    CheckF(lesserTwo)

  val check: CheckF[List[String], Int] =
    a and b

  println(check(9))
  println(check(1))
  println(check(-3))





}
