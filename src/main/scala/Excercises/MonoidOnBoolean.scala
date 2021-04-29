package Excercises

object MonoidOnBoolean extends App{
  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }
  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }
  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) =
      monoid }
  //AND
  implicit val booleanAndMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a && b
      def empty = true
    }

  println(booleanAndMonoid.combine(true,false))

  //OR
  implicit val booleanORMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a || b
      def empty = false
    }

  println(booleanORMonoid.combine(true,false))
  // Exclusive OR
  implicit val booleanEitherMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def combine(a: Boolean, b: Boolean) =
      (a && !b) || (!a && b)
    def empty = false
  }
  // the negation of exclusive or
  implicit val booleanXnorMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) =
        (!a || b) && (a || !b)
      def empty = true
    }

}
