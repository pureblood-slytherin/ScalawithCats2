package Chapters.MonadsC
import cats.Eval
import cats.syntax.functor._

object EvalAsMonad extends App{
  val myFirstEval = Eval.always{
    println("Step 1");"Welcome"
  }.map{string=>
    println("Step 2"); s"$string to Hogwarts!"
  }
  println(myFirstEval)
  println(myFirstEval.value)
  // With For comprehension

  val myFirstEvalWithFor = for{
    string<- Eval.always{println("Step 1");"Welcome"}
    finalString<- Eval.always{println("Step 2"); s"$string+ to Drumstrang!"}
  }yield finalString
  println(myFirstEvalWithFor)
  println(myFirstEvalWithFor.value)
  // play with now, always and later and you'll understand

  // .memomize function, memor
  val myFirstEvalWithFor2 = for{
    string<- Eval.always{println("Step 1");"Welcome"}.memoize
    finalString<- Eval.always{println("Step 2"); s"$string+ to Drumstrang!"}
  }yield finalString
  println(myFirstEvalWithFor2.value)
  println(myFirstEvalWithFor2.value)
  // Stack Safety : Trampolining and Eval.defer

  // Ugly Factorial method
  def factorial(n: BigInt): BigInt =
    if(n == 1) n else n * factorial(n - 1)

  //factorial(50000)  This will give a stack overflow

  def factorialEval(n:BigInt):Eval[BigInt]= {
    if (n == 1) {
      Eval.now(n)
    } else {
      Eval.defer(factorialEval(n - 1).map(_ * n))
    }
  }

  println(factorialEval(50000).value)


}
