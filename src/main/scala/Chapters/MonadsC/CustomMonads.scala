package Chapters.MonadsC

import cats.Monad
import scala.annotation.tailrec
import cats.syntax.flatMap._
import cats.syntax.functor._
//import cats.instances.option._
import cats.syntax.monad._


object CustomMonads extends App{
    val optionMonad = new Monad[Option] {
      def flatMap[A, B](opt: Option[A])(fn: A => Option[B]): Option[B] =
        opt.flatMap(fn)


      def pure[A](opt: A): Option[A] =
        Some(opt)

      @tailrec
      def tailRecM[A, B](a: A)(fn: A => Option[Either[A, B]]): Option[B] =
        fn(a) match {
          case None => None
          case Some(Left(a1)) => tailRecM(a1)(fn)
          case Some(Right(a2)) => Some(a2)
        }


    }

  def retry[F[_]: Monad,A](start: A)(f: A => F[A]): F[A] =
    f(start).flatMap{ a =>
    retry(a)(f)
  }

  retry(100)(a => if(a == 0) None else Some(a - 1)) // TODO Find why itsn't working
  //retry(100000)(a => if(a == 0) None else Some(a - 1))

  def retryTailRecM[F[_]: Monad, A](start: A)(f: A => F[A]): F[A] = Monad[F].tailRecM(start){ a =>
    f(a).map(a2 => Left(a2)) }

  retryTailRecM(100000)(a => if(a == 0) None else Some(a - 1))

  def retryM[F[_]: Monad, A](start: A)(f: A => F[A]): F[A] =
    start.iterateWhileM(f)(a => true)
  retryM(100000)(a => if(a == 0) None else Some(a - 1))
}
