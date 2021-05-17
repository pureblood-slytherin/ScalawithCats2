package CaeStudies
import cats.Id
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._
import cats.instances.list._
import cats.syntax.traverse._
import cats.Applicative
import cats.syntax.functor._

object AsynchronousTest extends App{

  trait getUpClient[F[_]]{
    def getUpTime(hostname: String): F[Int]
  }

  trait RealUpTimeClient extends getUpClient[Future]{
    def getUpTime(hostname: String): Future[Int]
  }

  trait TestUpTimeClient extends getUpClient[Id]{
    def getUpTime(hostname: String): Id[Int]
  }

  class upTimeService[F[_]: Applicative](client: getUpClient[F]){
    def getTotalUpTime(hostname:List[String]):F[Int]=
      hostname.traverse(client.getUpTime).map(_.sum)
  }



  class SumTestUpTimeClient(hosts : Map [String,Int] ) extends TestUpTimeClient {
    def getUpTime(hostname: String): Int =
      hosts.getOrElse(hostname,0)
  }


  def testTotalUpTime():Boolean={
    val hosts = Map("abc.com"->7,"amazon.in"->8)
    val client = new SumTestUpTimeClient(hosts)
    val service = new upTimeService(client)
    val actualSum = service.getTotalUpTime(hosts.keys.toList)
    val manualSum = hosts.values.sum
    actualSum==manualSum
  }

  println(testTotalUpTime())

}
