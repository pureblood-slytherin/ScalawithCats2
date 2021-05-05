package Excercises

import Chapters.FunctorsChap.FunctorCats.increment
import Chapters.FunctorsChap.Functors.Tree
import cats.Functor
import cats.syntax.functor._

object FunctorTree extends App{

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
