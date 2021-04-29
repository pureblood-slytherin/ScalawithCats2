package Excercises
import cats.Monoid
import cats.instances.int._
import cats.syntax.monoid._

import scala.annotation.tailrec

object AddingAllThings extends App{

  // Adding numbers
    // My Solution
    def add[A](items: List[A])(implicit monoid: Monoid[A]): A = {
      @tailrec
      def add_elements(head: A, tail: List[A], sum: A): A =
        if (tail.isEmpty) sum |+| head |+| monoid.empty
        else add_elements(tail.head, tail.tail, head |+| sum |+| monoid.empty)

      add_elements(items.head, items.tail, monoid.empty)
    }

  // Book Solution
  def addition[A](items: List[A])(implicit monoid: Monoid[A]): A ={
    items.foldLeft(monoid.empty)(_ |+| _)
  }

  val myList = List(2,4,5,6,7,2,1,3,4,67,689,34)
  //val myotherList = List(1,2,3)
  println(add(myList))
  println(addition(myList))
  //println(add(myotherList))

  // Adding orders
  case class Order(totalCost: Double, quantity: Double)

  // Total order is Sigma(totalCost*quantity)
  val myListOrder:List[Order] = List(Order(23,2),Order(34,3),Order(90,1))

  implicit val myOrderMonoid: Monoid[Order]= new Monoid[Order]{
    override def combine(x: Order, y: Order): Order =
      Order(
        x.totalCost + y.totalCost,
        x.quantity+y.quantity
      )

    override def empty: Order = Order(0,0)
  }
  println(addition(myListOrder))






}
