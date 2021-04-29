package Chapters.MonoidsSemigroups
object Monoidc extends App {

  implicit def setMon[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
    def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    def empty: Set[A] = Set.empty[A]
  }
  //val intSetMonoid = Monoid[Set[Int]]

  val Set1 = Set(1,2,3)
  val Set2 = Set(3,5,6)

  println(setMon.combine(Set1,Set2))


}

trait Monoid[A] {
  def combine(x: A, y: A): A
  def empty: A
}
