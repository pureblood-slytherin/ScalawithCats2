package Chapters.MonadsC

object EvalIntro extends App{
  //  call‐ by‐value

  val x = {
    println("Computing X")
    math.random()
  }
  println(x)
  println(x)
  // call‐by‐name

  def y = {
    println("Computing Y")
    math.random()
  }
  println(y)
  println(y)
  // call-by-need
  lazy val z = {
    println("Computing Z")
    math.random()
  }

  println(z)
  println(z)


  /**
   * • call‐by‐value which is eager and memoized;
     • call‐by‐name which is lazy and not memoized;
     • call‐by‐need which is lazy and memoized.
   */

  // Eval Monad
  /**
   * Now:
   * Always:
   * Later:
   * */
  import cats.Eval
  val now = Eval.now(math.random()+10)
  val always = Eval.always(math.random()+10)
  val later = Eval.later(math.random()+10)
  println("Without values")
  println(now)
  println(always)
  println(later)
  //1st time
  println("1st Time printing values")
  println(now.value)
  println(always.value)
  println(later.value)
  //2nd Time
  println("2nd Time printing values")
  println(now.value)
  println(always.value)
  println(later.value)


}
