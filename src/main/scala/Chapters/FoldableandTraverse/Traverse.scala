package Chapters.FoldableandTraverse
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Traverse extends App{
  val hostname = List("abc.com","fb.com","aws.amazon.com")
  def getUpTime(hostname : String):Future[Int]=
    Future(hostname.length*60)
  val AllupTime:Future[List[Int]]=
    hostname.foldLeft(Future(List.empty[Int])){(accum,host)=>
      val upTime = getUpTime(host)
      for{
      upTime <- upTime
      accum <- accum
    } yield accum :+ upTime
    }

  println(Await.result(AllupTime, 1.second))

  // Traverse for same function
  val AllUpTimeTraverse:Future[List[Int]]=
    Future.traverse(hostname)(getUpTime)

  println(Await.result(AllUpTimeTraverse,1.second))
}
