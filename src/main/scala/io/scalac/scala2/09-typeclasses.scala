package io.scalac.scala2

package typeclasses {

  case class Person(name: String, surname: String, age: Int)
  case class Car(brand: String, model: String, productionYear: Int)
  case class TaxiDriver(car: Car, driver: Person, licenseNumber: Int)

  object TypeClassDefinition {

    trait Show[A] {
      def show(a: A): String
    }

    implicit class ShowSyntax[A: Show](a: A) {
      def show: String = implicitly[Show[A]].show(a)
    }

    object Show {
      // pre-packaged show instances
      implicit val givenShowPerson: Show[Person] = new Show[Person] {
        def show(p: Person): String = s"${p.name} ${p.surname}"
      }

      implicit val givenShowCar: Show[Car] = c => s"${c.brand} ${c.model}"
    }
  }

  object ThirdPartyInstances {
    import TypeClassDefinition._

    implicit def givenShowTaxiDriver(implicit p: Show[Person], c: Show[Car]): Show[TaxiDriver] =
      t => s"${t.driver.show} #${t.licenseNumber} driving ${t.car.show}!"
  }

  object TypeClassUsageExample extends App {
    import TypeClassDefinition._
    import ThirdPartyInstances.givenShowTaxiDriver

    val person     = Person("John", "Doe", 42)
    val car        = Car("Toyota", "Avensis", 2011)
    val taxiDriver = TaxiDriver(car, person, 613)

    def printAll[A](items: A*)(implicit s: Show[A]) =
      items.foreach(i => println(i.show))

    printAll(person)
    printAll(car)
    printAll(taxiDriver)
  }

  /**
   * Context bound syntax is syntatic sugar for requiring an implicit instance of a type class for
   * given type parameter.
   */
  object ContextBoundSyntax extends App {
    import TypeClassDefinition._
    import ThirdPartyInstances.givenShowTaxiDriver

    val person     = Person("John", "Doe", 42)
    val car        = Car("Toyota", "Avensis", 2011)
    val taxiDriver = TaxiDriver(car, person, 613)

    def printAll[A: Show](items: A*) =
      items.foreach(i => println(i.show))

    printAll(person)
    printAll(car)
    printAll(taxiDriver)
  }
}

package graduation2 {

  /**
   * Using type classes and type class derrivation implement generic JsonEncoder.
   */
  trait JsonEncoder[A] {
    def encode(value: A): String
  }

  /**
   * Define instances of JsonEncoder for all primitive types as well as collections and `UUID`.
   */
  object JsonEncoder {

  }

  import java.util.UUID

  case class Person(id: UUID, firstName: String, lastName: String, age: Int, createdAt: Long, isActive: Boolean, friends: List[String])

  /**
   * Define a derrived JSON encoder for Person case class.
   */
  object Person

  /**
   * Run the encoder.
   */
  object EncodePersonExample extends App {
    
    val john = Person(UUID.randomUUID, "John", "Doe", 22, 10005000, true, List("Alice", "Max"))
    val jane = Person(UUID.randomUUID, "Jane", "Yui", 17, 20003333, false, List.empty)

    val persons = List(john, jane)

    def encode[A](value: A): String = ???

    println(encode(persons))
  }
}