package CaeStudies
import CaeStudies.CRDTs.BoundedSemiLattice.intBSL
import CaeStudies.CRDTs.wrapper.BoundedSemiLattice
import cats.kernel.CommutativeMonoid
import cats.syntax.semigroup._
import cats.syntax.foldable._
import cats.instances.list._
import cats.instances.map._
import cats.instances.int._


object CRDTsTypeClass extends App{
  trait GCounter[F[_,_],K, V] {
    def increment(f: F[K, V])(k: K, v: V)
                 (implicit m: CommutativeMonoid[V]): F[K, V]
    def merge(f1: F[K, V], f2: F[K, V])
             (implicit b: BoundedSemiLattice[V]): F[K, V]
    def total(f: F[K, V])
             (implicit m: CommutativeMonoid[V]): V
  }
  object GCounter {
    def apply[F[_,_], K, V]
    (implicit counter: GCounter[F, K, V]) =
      counter
  }

  implicit def mapGCounterInstance[K,V]:GCounter[Map,K,V]={
    new GCounter[Map,K,V] {
      def increment(map: Map[K, V])(k: K, v: V)
                   (implicit m: CommutativeMonoid[V]): Map[K, V] = {
        val value = map.getOrElse(k,m.empty) |+| v
        map + (k -> value)
      }

      def merge(f1: Map[K, V], f2: Map[K, V])
               (implicit b: BoundedSemiLattice[V]): Map[K, V] =
        f1 |+| f2



      def total(map: Map[K, V])
               (implicit m: CommutativeMonoid[V]): V =
        map.values.toList.combineAll
    }

  }

  val g1 = Map("a" -> 7, "b" -> 3)
  val g2 = Map("a" -> 2, "b" -> 5)
  val myCounter = GCounter[Map,String,Int]

  val merged =myCounter.merge(g1,g2)
  println(merged)
  //val total = myCounter.total(merged)  TODO WHy isn't Cat giving instance of Commutatative Monoid[Int]

  trait KeyValueStore[F[_,_]] {
    def put[K, V](f: F[K, V])(k: K, v: V): F[K, V]
    def get[K, V](f: F[K, V])(k: K): Option[V]
    def getOrElse[K, V](f: F[K, V])(k: K, default: V): V =
      get(f)(k).getOrElse(default)
    def values[K, V](f: F[K, V]): List[V]
  }



  implicit val mapKeyValueStoreInstance: KeyValueStore[Map] = new KeyValueStore[Map] {
    def put[K, V](f: Map[K, V])(k: K, v: V): Map[K, V] = f + (k -> v)
    def get[K, V](f: Map[K, V])(k: K): Option[V] = f.get(k)
    override def getOrElse[K, V](f: Map[K, V])
                                (k: K, default: V): V =
      f.getOrElse(k, default)
    def values[K, V](f: Map[K, V]): List[V] = f.values.toList
  }

  implicit class KvsOps[F[_,_], K, V](f: F[K, V]) { def put(key: K, value: V)
                                                           (implicit kvs: KeyValueStore[F]): F[K, V] =
    kvs.put(f)(key, value)
    def get(key: K)(implicit kvs: KeyValueStore[F]): Option[V] = kvs.get(f)(key)
    def getOrElse(key: K, default: V)
                 (implicit kvs: KeyValueStore[F]): V =
      kvs.getOrElse(f)(key, default)
    def values(implicit kvs: KeyValueStore[F]): List[V] = kvs.values(f)
  }
}
