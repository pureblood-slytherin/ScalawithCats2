package Excercises
import cats.data.Reader
import cats.syntax.applicative._ // for pure

object ReaderExcercise extends App{

  final case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
     )
  type DbReader[A] = Reader[Db, A]




  def takeUsername(Id:Int):DbReader[Option[String]]=
    Reader(database=> database.usernames.get(Id))

  def checkPassword(
                     username: String,
                     password: String): DbReader[Boolean] =
    Reader(db=>db.passwords.get(username).contains(password))

  def checkLogin(
                  userId: Int,
                  password: String): DbReader[Boolean] =
    for{
      userName <- takeUsername(userId)
      passwordOK<- userName.map {
        username =>checkPassword(username, password)
        }.getOrElse {
        false.pure[DbReader]}
    } yield passwordOK

  val users = Map( 1 -> "dade", 2 -> "kate", 3 -> "margo")
  val passwords = Map( "dade" -> "zerocool", "kate" -> "acidburn", "margo" -> "secret")
  val db = Db(users, passwords)

  val ans1= checkLogin(1,"zerocool").run(db)
  val ans2 = checkLogin(4, "davinci").run(db)
  println(ans1)
  println(ans2)

}
