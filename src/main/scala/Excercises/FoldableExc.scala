package Excercises

//import scala.::

object FoldableExc extends App{
  println(List(1, 2, 3).foldLeft(List.empty[Int])((a, b) => b :: a))

  // Scafolding other methods
  // Map using fold
  def map[A, B](list: List[A])(func: A => B): List[B] =
    list.foldRight(List.empty[B]) { (item, accum) =>
    func(item) :: accum
  }

  println(map(List(1, 2, 3))(_ * 2))

  // Flatmap using Fold ::: join and make a new list
  def flatMap[A, B](list: List[A])(func: A => List[B]): List[B] =
    list.foldRight(List.empty[B]) { (item, accum) =>
    func(item) ::: accum
  }

  println(flatMap(List(1, 2, 3))(a => List(a, a * 10, a * 100)))

  // Filter using Fold
  def filter[A](list: List[A])(func: A => Boolean): List[A] =
    list.foldRight(List.empty[A]) { (item, accum) =>
    if(func(item)) item :: accum else accum
  }

  println(filter(List(1, 2, 3))(_ % 2 == 1))
}
