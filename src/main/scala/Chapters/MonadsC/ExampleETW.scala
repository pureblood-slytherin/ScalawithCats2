package Chapters.MonadsC

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object ExampleETW {

  // Example 1 Census

  case class person(firstName: String, lastName: String) {
    assert(firstName != null && lastName != null)
  }

  // Census API
  //Method 1 if null
  def getPersonIf(firstName: String, lastName: String): person =
    if (firstName != null) {
      if (lastName != null) {
        person(firstName, lastName)
      } else null
    } else null

  // Method 2 Option
  def getPersonOption(firstName: String, lastName: String): Option[person] =
    Option(firstName).flatMap { fName =>
      Option(lastName).flatMap { lName =>
        Option(person(fName,lName))
      }
    }

  // Method 3 Option using For and Yield
  def getPersonFor(firstName: String, lastName: String): Option[person] = for{
    fname <- Option(firstName)
    lname <- Option(lastName)
  } yield person(fname,lname)     // Todo Doubt: Why Option(Person) was not used

  // Example 2 : Asynchronous fetches
  case class user(id:String)
  case class product(Sku:String, price: Double)
  def getUser(url: String):Future[user]=Future{
    user("Abhijeet") // sample implimentation
  }
  def getLastOrder(userId:String):Future[product]= Future{
    product("123-389",42.25) // sample
  }
  val myUrl = "tatacliq.com/ac/splitac/voltas-1-ton"
  //ETW Method 1
  /**
  getUser(myUrl).onComplete{
    case Success(user(id)) =>      // Extracting user once it is completed
      val lasOrder = getLastOrder(id)
      lasOrder.onComplete{
        case Success(product(sku,price)) =>// Extracting product once it is completed
          val GSTIncludedPrice = 1.18*price  // Transforming
          // Send the value to user          Wrap
      }
  }
  */
  // ETW Method 2
  val GSTPriceMethod2 :Future[Double]= getUser(myUrl).flatMap(
    user=> getLastOrder(user.id))
    .map(_.price*1.18)

  // ETW Method 3
  val GSTPriceMethod3:Future[Double] = for{
    user <- getUser(myUrl)
    product <- getLastOrder(user.id)
  } yield product.price*1.18

  // Example 3
  val number = List(1,2,3)
  val chars = List('a','b','c')
  val myListofTuples:List[(Int,Char)]= number.flatMap (n=> chars.map(letter=>(n,letter)))
  //println(myListofTuples)
  val myListofTuplesFor:List[(Int,Char)] = for{
    n<-number
    letter<-chars
  }yield (n,letter)
  //println(myListofTuplesFor)

}

