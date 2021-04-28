package Excercises

trait printable[A]{
  def myPrint(value:A):String
}

object PrintableInstances {
  implicit val myIntPrint = new printable[Int] {
    override def myPrint(value: Int): String = value.toString
  }

  implicit val myStringPrint = new printable[String] {
    override def myPrint(value: String): String = value
  }
}

// interface
object printable{
  def format[A](value: A)(implicit method: printable[A]):String =
    method.myPrint(value)

  def autoprintMethod[A](value: A)(implicit method: printable[A]):Unit =
    println(method.myPrint(value))
}




