package CaeStudies

import CaeStudies.CRDTs.wrapper.BoundedSemiLattice


object CRDTs extends App{
  case class GCounters(counters: Map[String,Int]){
    def increment(machine:String,amount: Int) ={
      val value = amount + counters.getOrElse(machine, 0)
      GCounters(counters + (machine->value))
    }
    def merge(that: GCounters): GCounters = {
      GCounters(that.counters ++ this.counters.map{
        case (k,v) =>
          k->(v max counters.getOrElse(k,0))
      })
    }

    def total:Int =
      this.counters.values.sum
  }
  import cats.kernel.CommutativeMonoid
  object wrapper{
    trait BoundedSemiLattice[A] extends CommutativeMonoid[A] {
      def combine(a1: A, a2: A): A
      def empty: A
    }
  }
  object BoundedSemiLattice {
    implicit val intBSL: BoundedSemiLattice[Int]=
      new BoundedSemiLattice[Int]{
        def combine(a1: Int, a2: Int): Int = a1 max a2
        val empty: Int = 0
      }

    implicit def SetBSL[A] : BoundedSemiLattice[Set[A]] =
      new BoundedSemiLattice[Set[A]] {
        def combine(a1: Set[A], a2: Set[A]): Set[A] = a1 union a2
        val empty: Set[A] = Set.empty[A]
      }
  }

  case class GCountersGeneric[A](counters: Map[String,A]) {
    def increment(machine:String,amount: A):GCountersGeneric[A] = ???
  }

}
