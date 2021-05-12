package Chapters.SemigropalApplicative
import cats.Semigroupal
import cats.instances.option._
import cats.syntax.apply._
import cats.syntax.semigroup._
import cats.Monoid
import cats.instances.int._
import cats.instances.invariant._
import cats.instances.string._

object Semigropal extends App{
  /**    DEFINITION
   * trait Semigroupal[F[_]] {
      def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
    } */
  val myNewGroup = Semigroupal[Option].product(Some("ABC"),Some("Murder"))
  println(myNewGroup)
  val myNone = Semigroupal[Option].product(None,Some("Kuch bhi"))
  println(myNone)

  val myTrio = Semigroupal.tuple3(Option("Harry"),Option("Ron"),Option("Hermoine")) // Option.empty(Type)
  println(myTrio)
  // Map2 to Map22
  val addNumbers = Semigroupal.map3(Option(2),Option(9),Option(1))(_+_+_)
  println(addNumbers)
  // Semigropal, product must be associative

  val myEasyGroup = (Option(1),Option("abc"),Option(67)).tupled
  println(myEasyGroup)

  // MapN
  case class wizard(name:String,age:Int,house:String)

  val myWizard = (
    Option("Hermoine"),
    Option(14),
    Option("Gryffindor")
    ).mapN(wizard)
  println(myWizard)

  val add: (Int, Int) => Int = (a, b) => a + b
  println((Option(8),Option(7)).mapN(add))

  val tupleToWizard: (String,Int,String)=>wizard =
    wizard.apply
  val harry = tupleToWizard("Harry",11,"Gryffindor")
  println(harry)
  val WizardToTuple: wizard=>(String,Int,String)=
    wizard=>(wizard.name,wizard.age,wizard.house)

  println(WizardToTuple(harry))

  //
  implicit val catMonoid: Monoid[wizard] = (
    Monoid[String],
    Monoid[Int],
    Monoid[String]
    ).imapN(tupleToWizard)(WizardToTuple)

  val Ginny = wizard("Ginny",10,"Gryffindor")
  val Harry = wizard("Harry",11,"Gryffindor")
  val HarryGinny = Harry |+| Ginny





}
