package Chapters.MonadsC
import cats.data.Reader

object ReaderMonad extends App{
  final case class Cat(name: String, favoriteFood: String)
  val cat = Cat("Crookshanks","Peter")

  val catName: Reader[Cat,String]=
    Reader(cat=>cat.name)

  println(catName.run(cat))

  val greetTheCat: Reader[Cat,String]=
    catName.map(s=> s"Hello $s")

  println(greetTheCat.run(cat))

  val feedKitty: Reader[Cat,String]=
    Reader(cat=>s"Have a nice bowl of ${cat.favoriteFood} ")

  val greetAndFeed: Reader[Cat,String]= for
    {
    greet<- greetTheCat
    feed<- feedKitty
  } yield s"$greet. $feed"
  println(greetAndFeed.run(cat))





}
