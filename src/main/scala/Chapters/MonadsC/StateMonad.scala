package Chapters.MonadsC
import cats.data.State
import State._


object StateMonad extends App{

  val a = State[Int, String]{ state =>
    (state, s"The state is $state")
  }
  println(a.run(9).value)
  println(a.runS(9).value)
  println(a.runA(9).value)

  val step1 = State[Int,String]{num=>
    val ans = num+2
    (ans,s"Our 1st number is $ans")
  }
  val step2 = State[Int,String]{num=>
    val ans = num*2
    (ans,s"Our 2nd number is $ans")
  }
  val both = for{
    a<- step1
    b<- step2
  }yield (a,b)

  println(both.run(8).value)
  /**
   *
  • get extracts the state as the result;
  • set updates the state and returns unit as the result;
  • pure ignores the state and returns a supplied result;
  • inspect extracts the state via a transformation function;
  • modify updates the state using an update function.
   * */
  val getDemo = State.get[Int]
  println(getDemo.run(10).value)

  val setDemo = State.set[Int](30)
  println(setDemo.run(10).value)

  val pureDemo = State.pure[Int, String]("Result")
  println(pureDemo.run(10).value)

  val inspectDemo = State.inspect[Int, String](x => s"${x}!")
  println(inspectDemo.run(10).value)

  val modifyDemo = State.modify[Int](_ + 1)
  println(modifyDemo.run(10).value)

  // Using for comprehension
  val program: State[Int, (Int, Int, Int)] = for {
       a <- get[Int]
       _ <- set[Int](a + 1)
       b <- get[Int]
       _ <- modify[Int](_ + 1)
       c <- inspect[Int, Int](_ * 1000)
       } yield (a, b, c)
  val (state, result) = program.run(1).value
  println((state, result))


}
