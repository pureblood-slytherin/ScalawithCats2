package Chapters.MonadsC

object MonadsAxioms {
  // Property 1 "LEFT IDENTIY"
  def numberandAquare(n:Int)= List(n,n*n)
  numberandAquare(4)
  List(4).flatMap(numberandAquare)
  //Both are same and return List(4,16)
  //Monad(x).flatMap(Function)==Function(x)   Property1

  // Propert 2 "RIGHT IDENTITY", "USELESS WRAP"
  List(1,2,3).flatMap(x=>List(x))               // List(1,2,3)
  //Monad(v).flatmap(x=>Monad(x)) == Monad(v)   // USELESS

  // Property 3 ASSOCIATIVITY ETW->ETW
  // Monad(v).flatMap(f).flatMap(g) == Monad(v).flatMap(x=> f(x).flatMap(g))
  val myList=List(1,2,3)
  def incrementer(n:Int) = List(n,n+1)
  def doubler(n:Int)=List(n,n*2)

  def main(args: Array[String]): Unit = {
    val myNewList = myList.flatMap(incrementer).flatMap(doubler)
    val myNewList2 = myList.flatMap(x=> incrementer(x).flatMap(doubler))
    println(myNewList==myNewList2)

  }



}
