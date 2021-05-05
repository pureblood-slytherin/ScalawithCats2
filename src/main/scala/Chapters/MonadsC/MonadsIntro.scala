package Chapters.MonadsC

object MonadsIntro {
  case class safeValue[+T](private val internalValue: T){
    def get:T = synchronized(  // Constructor = PURE or UNIT
      internalValue
    )
    def flatMap[A](transformer:T => safeValue[A] ):safeValue[A]= synchronized ( // bind or flatmap
      transformer(internalValue)
    )
  }
  // external API
  def getSafeValue[T](value:T): safeValue[T] = safeValue(value)

  // ETW
  val SafeString = safeValue("Welcome to Hogwarts")
  //Extract
  val getSafeString = SafeString.get
  // Transform
  val upperString = getSafeString.toUpperCase()
  //wrap
  val upperSafeString = safeValue(upperString)

  // Direct way of doing it, add a transform(flatMap) function
  val upperSafeString2 = SafeString.flatMap(value => safeValue(value.toUpperCase))


}
