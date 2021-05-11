package Chapters.MonadTransformers

import cats.data.{EitherT, OptionT, WriterT}
import cats.instances.either._
import cats.instances.list._
import cats.syntax.applicative._

import scala.concurrent.Future
import cats.instances.future._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Try


object MTransformers extends App{

  /**
  def lookupUserName(id:Long):Either[Error,Option[String]]=
    for{
      userOption<-lookupUser(id)
    }yield for{
      myUser<- userOption
    }yield myUser.name
    */
  type ListOption[A] = OptionT[List,A]
  val result1:ListOption[Int] = OptionT(List(Option(10)))
  val result2:ListOption[Int] = 23.pure[ListOption]

  val result3 = result1.flatMap(x=> result2.map(y=> x+y))
  println(result3.value)
  // Monads Transformer in Cats

  type ErrorOr[A] = Either[String, A]
  type ErrorOption[A]= OptionT[ErrorOr,A] // ErrorOr[Option[A]] => Either[Option[A],String]

  val myEither =  10.pure[ErrorOption]
  println(myEither.value)
  val myEither2 = 21.pure[ErrorOption]
  val myEither3 = myEither.flatMap(x=>
  myEither2.map(y=> x+y))

  println(myEither3.value)

  // a Future of an Either of Option
  type futureEither[A] =EitherT[Future,String,A]
  type myFutureEitherofOption[A] = OptionT[futureEither,A]

  val myValOfFutureEitherOption:myFutureEitherofOption[Int]= for{
    a<- 10.pure[myFutureEitherofOption]
    b<- 21.pure[myFutureEitherofOption]
  } yield a+b
  //Thread.sleep(1000)
  println(myValOfFutureEitherOption.value)

  //Constructing and Unpacking Instances
  val myError = new NumberFormatException
  val errorStack = myError.pure[ErrorOption]
  println(errorStack.value.map(_.getOrElse(-1)))

  val intermediate = myValOfFutureEitherOption.value
  val stack = intermediate.value
  val answer = Await.result(stack,1.second)
  println(answer)

  import cats.data.Writer

  type Logged[A] = Writer[List[String],A]

  def parseNumber(str: String): Logged[Option[Int]] =
    Try(str.toInt).toOption match {
    case Some(num) => Writer(List(s"Read $str"), Some(num))
    case None      => Writer(List(s"Failed on $str"), None)
  }
  def addAll(a: String, b: String, c: String): Logged[Option[Int]]= {
    import cats.data.OptionT
    val result = for{
      a1<- OptionT(parseNumber(a))
      b1<- OptionT(parseNumber(b))
      c1<- OptionT(parseNumber(c))
    }yield a1+b1+c1
    result.value
  }
  val Myresult1 = addAll("1", "2", "3")
  println(Myresult1)
  val Myresult2 = addAll("1", "a", "3")
  println(Myresult2)


}
