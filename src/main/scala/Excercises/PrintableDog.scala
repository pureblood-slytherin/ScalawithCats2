package Excercises
import PrintableInstances._
import printable._


final case class Dog(val name:String,val age:Int,val color:String )

object PrintableDog {
  implicit val printDog = new printable[Dog] {

    override def myPrint(value: Dog): String = {
      val Name = printable.format(value.name)
      val Age = printable.format(value.age)
      val Color = printable.format(value.color)
      s"$Name is a $Age year-old $Color dog."

    }
  }
}

object printableSyntax{
  implicit class PrintableOps[A](value:A){
    def format(implicit printable: printable[A]):String =
      printable.myPrint(value)

    def autoprintMethod(implicit printable: printable[A]):Unit =
      println(printable.myPrint(value))

  }
}

