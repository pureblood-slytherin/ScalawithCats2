package Chapters.FunctorsChap
import Chapters.FunctorsChap.FunctorCats.increment
import Chapters.FunctorsChap.Functors.Tree
import cats.Functor
import cats.syntax.functor._

object Functors{
  trait Tree[+A]

  case class Branch[A](value:A,left: Tree[A], right: Tree[A]) extends Tree[A]

  case class Leaf[A](value: A) extends Tree[A]

  object Tree{
    def leaf[T](value:T):Tree[T] = Leaf(value)
    def branch[T](value:T,left: Tree[T], right: Tree[T]):Tree[T] = Branch(value,left,right)
  }

  // General functor for any customised instance
  // STABLE API
  def increment[F[_]](function: F[Int])(implicit functor: Functor[F]):F[Int]=
    function.map(_+1)


  // defining implicit functor for type tree
  implicit val myTreeFunctor : Functor[Tree]=
    new  Functor[Tree] {
      override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match{
        case Leaf(value) => Leaf(f(value))
        case Branch(value,left,right) => Branch(f(value),map(left)(f),map(right)(f))
      }
    }
}

object InstanceOfCustomType extends App{

  val myTree = Tree.branch(3,Tree.branch(1,Tree.leaf(2),Tree.leaf(4)),
    Tree.branch(4,Tree.leaf(9),Tree.leaf(7)))
  //          3
  //          /\
  //        1   4
  //        /\  /\
  //       2 4  9 7

  println(increment(myTree))
  println(myTree.map(_*10)) // Just like this
  println(myTree.map(_*2).map(_*(-1)))

}
