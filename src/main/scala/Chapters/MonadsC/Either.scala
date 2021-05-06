package Chapters.MonadsC
import cats.syntax.either._
//import cats.Monad
//import cats.implicits._
//import cats.instances._
// import EitherStyle._


object Either extends App{
  val firstEither:Either[String,Int] = Right(5)
  val secondEither:Either[String,Int] = Right(19)
  val sumofEither = for{
    a<- firstEither
    b<- secondEither
  }yield a+b
  println(sumofEither)

  val a = 5.asRight[String] //   same as val firstEither:Either[String,Int] = Right(5)
  val b= 19.asRight[String]

  def countPositive(nums: List[Int]) =
    nums.foldLeft(0.asRight[String]){ (accumulator,num) =>
      if(num>0){
        accumulator.map(_ + 1)
      } else{
        Left("Negative. Stopping!")
      }
    }
  println(countPositive(List(2,1,3)))

  // TODO : Why is catchOnly and fromTry not working??
  /**
  val either: Either[NumberFormatException, Int] =
    Either.catchOnly[NumberFormatException]("abc".toInt)

  Either.fromTry(scala.util.Try("foo".toInt))
  */
  println("Error".asLeft[Int].getOrElse(0))
  // Ensure method
  println((-1).asRight[String].ensure("Must be non-negative!")(_ > 0))

  "foo".asLeft[Int].leftMap(_.reverse)
  // Either[String, Int] = Left("oof")
  6.asRight[String].bimap(_.reverse, _ * 7)
  // Either[String, Int] = Right(42)
  "bar".asLeft[Int].bimap(_.reverse, _ * 7)
  // Either[String, Int] = Left("rab")

  println("Myname".asRight[Int].bimap(_*3,_.reverse))
  println("Myname".asRight[Int].bimap(_*3,_.reverse).swap)
  println(4.asLeft[String].leftMap(_*7))

  // Copy pasted Example of how can Wither be used for handling error
  object wrapper {
    sealed trait LoginError extends Product with Serializable
    final case class UserNotFound(username: String)
      extends LoginError
    final case class PasswordIncorrect(username: String) extends LoginError
    case object UnexpectedError extends LoginError
  };
  import wrapper._
  case class User(username: String, password: String)
  type LoginResult = Either[LoginError, User]

  // Choose error-handling behaviour based on type:
  def handleError(error: LoginError): Unit =
    error match {
      case UserNotFound(u) =>
        println(s"User not found: $u")
      case PasswordIncorrect(u) =>
        println(s"Password incorrect: $u")
      case UnexpectedError =>
        println(s"Unexpected error")
    }

  val result1: LoginResult = User("dave", "passw0rd").asRight // result1: LoginResult = Right(User("dave", "passw0rd"))
  val result2: LoginResult = UserNotFound("dave").asLeft     // result2: LoginResult = Left(UserNotFound("dave"))

  result1.fold(handleError, println)
  result2.fold(handleError, println)


}
