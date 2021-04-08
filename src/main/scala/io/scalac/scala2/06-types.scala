package io.scalac.scala2

/**
 * In Scala all expressions evaluate to values, and each value is of certain type.
 * 
 * A type can be viewed as a "set of values", for example:
 *   `Boolean` type represents two values: `true` or `false`
 *   `Int`     type represents 2^32 values: numbers between -2^31 and (2^31 - 1)
 *   `List`    type represents an infinite number of values (you can always construct a new list, by adding another element)
 */
package types {

  object primitives {

    // numeric
    val byte   : Byte   = 10
    val short  : Short  = 1000
    val int    : Int    = 1000000
    val long   : Long   = 10000000000L
    val double : Double = 3.14
    val float  : Float  = 6.28f

    // other
    val bool  : Boolean = true
    val char  : Char    = 'a'
    val unit  : Unit    = ()
    val null_ : Null    = null
  }

  /**
   * Types in Scala can be related via subtyping relationship.
   * Types in Scala form a lattice (as opposed to hierarchy in Java), which is to say that there is
   *   - a "top" type (Any) which is the supertype of all types,
   *   - a "bottom" type (Nothing) which is the subtype of all types.
   */
  object subtyping {

    /**
     * EXERCISE 1
     * 
     * Provide a valid instance of type `Any`.
     */
    val anything : Any = ???

    /**
     * EXERCISE 2
     * 
     * Provide a valid instance of type `AnyVal`.
     */
    val anyValue : AnyVal = ???

    /**
     * EXERCISE 3
     * 
     * Provide a valid instance of type `AnyRef`.
     */
    val anyObject : AnyRef = ???

    /**
     * EXERCISE 4
     * 
     * Try to provide a valid instance of type `Nothing`. Note your findings.
     */
    val nothing : Nothing = ???

    /**
     * A subtype is guaranteed to contain at least all the members of it's supertype, therefore
     * a subtype can always be viewed as it's supertype.
     * 
     * In short the subtype is more specific than it's supertype.
     * 
     * Coming back to "types as sets of values" perspective, the subtype is a subset of values
     * that conform to the more strict (specific) structure.
     */
    trait Mammal {
      def age: Int
    }

    trait Human extends Mammal {
      def name: String 
      def surname: String 
    }

    class Person(val name: String, val surname: String, val age: Int) extends Human
    class Employee(name: String, surname: String, age: Int, val salary: Int) extends Person(name, surname, age)

    def show(human: Human): Unit = println(s"${human.name} ${human.surname} is ${human.age} years old.")

    val employee: Employee = new Employee("Johny", "Bravo", 22, 1000)

    /**
     * EXERCISE 5
     * 
     * Try to extract fields and run show using an instance of Person. Note your findings.
     */
    object personEx {
      val person  : Person = employee
      val age     : Int    = ???
      val name    : String = ???
      val surname : String = ???
      val salary  : Int    = ???
      show(???)
    }

    /**
     * EXERCISE 6
     * 
     * Try to extract fields and run show using an instance of Human. Note your findings.
     */
    object humanEx {
      val human   : Human  = employee
      val age     : Int    = ???
      val name    : String = ???
      val surname : String = ???
      val salary  : Int    = ???
      show(???)
    }

    /**
     * EXERCISE 7
     * 
     * Try to extract fields and run show using an instance of Mammal. Note your findings.
     */
    object mammalEx {
      val mammal  : Mammal = employee
      val age     : Int    = ???
      val name    : String = ???
      val surname : String = ???
      val salary  : Int    = ???
      show(???)
    }
  }

  /**
   * A Product type is essentially a way of combining multiple values inside of one.
   * 
   * Product types are compound types that contain all elements of their constituents.
   * For every element that makes the product we have a function that will return that element.
   */
  package productTypes {

    /**
     * The simplest example of a Product type in Scala is a Tuple.
     */
    object tuples {

      /**
       * The (A, B, ..., N) syntax is a shorthand for TupleN[A, B, ..., N]
       */
      val tuple3  : Tuple3[Int, Int, Int] = Tuple3(4, 2, 1)
      val point3d : (Int, Int, Int)       = (4, 2, 1)

      /**
       * EXERCISE 1
       * 
       * Extract individual elements by their position.
       */
      val x: Int = ???
      val y: Int = ???
      val z: Int = ???

      /**
       * EXERCISE 2
       * 
       * Add another element to tuple of size 22. Note your findings.
       */
      val tuple22 = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22)

      /**
       * EXERCISE 3
       *
       * Construct a tuple representing persons name, surname and age.
       */
      val person: (String, String, Int) = ???

      /**
       * EXERCISE 4
       *
       * Deconstruct the person tuple to extract the name, surname and age.
       */
      val (name, surname, age) = (???) : (String, String, Int)

      /**
       * EXERCISE 5
       *
       * Pattern match on person tuple to extract name and surname and combine them to a single value.
       */
      val fullName: String = person match {
        case _ => ???
      }
    }

    /**
     * Another way of modelling Product types in Scala is by using a Case Class.
     * 
     * A case class is a regular Scala class that gets special treatment by the compiler.
     * You can view a case class as a tuple with labelled elements.
     */
    object caseClass {

      /**
       * Defining a case class is as simple as adding `case` modifier to a regular class.
       */
      case class Person(name: String, surname: String)

      /**
       * The compiler will autogenerate an `apply` method in the companion object of our case class,
       * therefore we can skip the `new` keyword when constructing case class instances.
       */
      val person: Person = Person("Ada", "Lovelace")

      /**
       * The compiler will treat all constructor parameters as immutable publicly accessible fields,
       * as if we marked each with a `val` keyword.
       */
      class PersonEquivalent(val name: String, val surname: String)

      /**
       * The compiler will autogenerate an `unapply` method in the companion object of our case
       * class, therefore we get an Extractor Object for free.
       */
      val isAda: Boolean = person match {
        case Person("Ada", _) => true
        case _                => false
      }

      /**
       * The compiler will autogenerate a `copy` method in the class which performs a shallow copy.
       */
      val ada: Person = ada.copy()

      /**
       * Optionally we can override any of the constructor arguments.
       */
      val william : Person = ada.copy(name = "William", surname = "King-Noel")
      val byron   : Person = william.copy(name = "Byron")
      val anne    : Person = william.copy(name = "Anne")
      val ralph   : Person = william.copy(name = "Ralph")

      /**
       * The compiler will autogenerate an `equals` method in the class which compares instances by
       * their values, as opposed to standard object comparison by reference.
       */
      val isTrue  : Boolean = ada == person
      val isFalse : Boolean = ada == william

      case class UserProfile(name: String, surname: String, age: Int, nickname: String)
      case class User(id: Int, email: String, profile: UserProfile, isActive: Boolean)

      /**
       * EXERCISE 1
       * 
       * Update the user email using the `copy` method.
       */
      def updateEmail(user: User, newEmail: String): User = ???

      /**
       * EXERCISE 2
       * 
       * Update the user nickname using the `copy` method.
       */
      def updateNickname(user: User, newNickname: String): User = ???

      /**
       * EXERCISE 3
       * 
       * Assume the two user accounts can be merged together, if they differ only by id.
       * 
       * Use the autogenerated `copy` and `equals` to implement this method.
       */
      def canBeMerged(user1: User, user2: User): Boolean = ???
    }
  }

  /**
   * Sum type is a type that is composed of diffrent possible values.
   * 
   * The simplest possible example is an enumeration. Java enums are sum types.
   * Scala models sum types using inheritance.
   */
  package sumTypes {

    /**
     * The simplest example of a Sum type in Scala is Either.
     */
    object either {

      type Success = Int
      type Error   = String
      
      /**
       * This function makes it explicit at type level that it can fail.
       */
      def division(dividend: Int, divisor: Int): Either[Error, Success] =
        if (divisor == 0) Left("Division by zero is undefined")
        else              Right(dividend / divisor)

      /**
       * The type signature forces us to handle all possible cases.
       */
      val result: String = division(10, 2) match {
        case Right(n)  => s"The result is $n"
        case Left(err) => err
      }

      object validations {
        def notEmpty(p: String) : Either[String, String] = Either.cond(p.nonEmpty, p, "Password cannot be empty")
        def minLength(p: String): Either[String, String] = Either.cond(p.length > 10, p, "Password must be at least 10 characters long")
        def maxLength(p: String): Either[String, String] = Either.cond(p.length < 32, p, "Password may not exceed 32 characters")
        def hasLower(p: String) : Either[String, String] = Either.cond(p.toUpperCase != p, p, "Password must contain lowercase characters")
        def hasUpper(p: String) : Either[String, String] = Either.cond(p.toLowerCase != p, p, "Password must contain uppercase characters")
      }

      /**
       * EXERCISE 1
       * 
       * Either is a data type that represents disjunction and provides many useful combinators.
       * 
       * Use the `flatMap` method to combine multiple validations into one.
       */
      def validate(plainPassword: String): Either[String, String] = ???

      /**
       * EXERCISE 2
       * 
       * Using `getOrElse` method set the dial to preferred temperature or default to 18 °C.
       */
      val preferenceResponse: Either[String, Double] = Left("Could not connect to preferences API.")
      val temperatureDial: Int = ???  
    }

    /**
     * The sealed modifier means that all subtypes of must be defined in the same file.
     * This limitation allows Scala to perform exhaustiveness checks when pattern matching.
     */
    object sealedTraits {

      /**
       * A finite enumeration. Could be modeled using Java enums.
       */
      sealed trait Color
      case object Red   extends Color
      case object Green extends Color
      case object Blue  extends Color

      /**
       * EXERCISE 1
       * 
       * First comment out one of the cases and compile.
       * Then remove the sealed modifier from Color enumeration and compile.
       * 
       * Note your findings.
       */
      def toRGB(color: Color): (Int, Int, Int) = color match {
        case Red   => (255, 0, 0)
        case Green => (0, 255, 0)
        case Blue  => (0, 0, 255)
      }

      /**
       * An infinite enumeration. Cannot be modeled using Java enums.
       */
      sealed trait Employee
      case object CEO extends Employee
      case object CTO extends Employee
      case class Dev(language: String) extends Employee

      /**
       * EXERCISE 2
       * 
       * Add `experience` Int field to `Dev` case and prefix the title with "junior" or "senior".
       */
      def title(employee: Employee): String = employee match {
        case CEO           => "CEO"
        case CTO           => "CTO"
        case Dev(language) => s"$language developer"
      }

      /**
       * A recursive definition. Cannot be modeled using Java enums.
       */
      sealed trait Natural
      case object Zero               extends Natural
      case class Succ(prev: Natural) extends Natural

      /**
       * EXERCISE 3
       * 
       * Translate the Natural number into an Int. Ignore int overflowing.
       * 
       * Hint: whenever you see recursive data type, you will need to use recursion.
       */
      def toInt(natural: Natural): Int = ???
    }
  }

  /**
   * Generic types are type constructors that take type parameters and return a concrete type. They
   * are used in Scala standard library to encode generic collection types that abstract over the
   * element type.
   */
  package generics {

    /**
     * We know nothing about the type A, except that all types have a common supertype `Any`.
     * 
     * `Any` defines the common structure shared by all values in Scala, among others:
     *   `equals: Any => Boolean` comparing objects for equivalence
     *   `toString: () => String` returning a string representation of the object
     * 
     * Refer to docs to see all members: https://www.scala-lang.org/api/current/scala/Any.html
     */
    case class Box[A](value: A) { self =>
      def show: Unit = println(s"A box with ${value.toString}")

      def compare[B](that: B): Unit =
        if (self equals that) println("It's the same thing")
        else                  println("They are diffrent things")
    }

    object BoxExample extends App {
      val box1 = Box("John")
      box1.show
      box1.compare(box1)

      val box2 = Box("Jane")
      box2.show
      box2.compare(box1)
    }

    /**
     * A Monad is a box, that can be transformed and combined with other boxes, preserving some
     * properties called "monadic laws".
     */
    case class Monad[A](value: A) {

      /**
       * EXERCISE 1
       * 
       * Implement polymorphic `map` method will transform the box into a diffrent type of box.
       */
      def map[B](f: A => B): Monad[B] = ???

      /**
       * EXERCISE 2
       * 
       * Implement polymorphic `flatMap` method will transform the box into a diffrent type of box.
       */
      def flatMap[B](f: A => Monad[B]): Monad[B] = ???
    }

    object MonadicLawsExample extends App {

      /**
       * A "pure" function for a monad. If we stick to the box analogy this function is just
       * packing the value into the "Monad" box.
       */
      def pure[A](v: A): Monad[A] = Monad(v)

      val value: Int = 3
      val monad: Monad[Int] = pure(value)

      val f: Int => Monad[Int] = n => Monad(n * 2)
      val g: Int => Monad[Int] = n => Monad(n + 7)
      
      val leftIdentityHolds =
        monad.flatMap(f) == f(value)

      if (leftIdentityHolds) println("Left identity law holds")
      else                   println("Left identity law broken")

      val rightIdentityHolds =
        monad.flatMap(pure) == monad

      if (rightIdentityHolds) println("Right identity law holds")
      else                  println("Right identity law broken")

      val isAssociative =
        monad.flatMap(f).flatMap(g) == monad.flatMap(f(_).flatMap(g))

      if (isAssociative) println("Associativity law holds")
      else               println("Associativity law broken")
    }

    /**
     * Monads represent sequential composition. We can chain a bunch of functions representing
     * steps in some sequential routine.
     */
    object MonadCompositionExample extends App {

      def pay(amount: Int, balance: Int): Monad[Int]  = Monad(balance - amount)
      def earn(amount: Int, balance: Int): Monad[Int] = Monad(balance + amount)

      def show(start: Int, end: Int): Unit =
        println(s"Started the day with $start euro, finished with $end euro.")

      val payForBreakfast = pay(8, _)
      val payForLunch     = pay(16, _)
      val earnTips        = earn(12, _)

      def showEndOfDayBalance(b1: Int): Monad[Unit] =
        payForBreakfast(b1).flatMap { b2 =>
          payForLunch(b2).flatMap { b3 =>
            earnTips(b3).map { b4 =>
              show(b1, b4)
            }
          }
        }

      showEndOfDayBalance(42)
    }

    /**
     * EXERCISE 3
     * 
     * Monadic composition is just a series of `flatMaps` followed by the final `map`.
     * 
     * Rewrite `showEndOfDayBalance` from previous example using for comprehension.
     */
    object MonadicForComprehensionExample extends App {
      import MonadCompositionExample.{payForBreakfast, payForLunch, earnTips, show}

      def showEndOfDayBalance(start: Int): Monad[Unit] = ???

      showEndOfDayBalance(42)
    }
  }

  /**
   * Generic types are by default invariant. Much means they relationship of their input types is
   * not preserved for the generic type.
   */
  package variance {

    trait Animal
    trait Dog extends Animal

    class Invariant[A] {
      def canProduce(): A = ???
      def canConsume(value: A): Unit = ???
    }

    class Covariant[+A] {
      def canProduce(): A = ???
    }

    class Contravariant[-A] {
      def canConsume(value: A): Unit = ???
    }

    /**
     * EXERCISE 1
     * 
     * `A <:< B` value can be generated by the compiler if it can proove that A is a subtype of B.
     * 
     * Uncomment following code and note which statements the compiler is able to proove.
     */
    object animalProofs {
      // val subtypeProof   = implicitly[Dog <:< Animal]
      // val supertypeProof = implicitly[Animal <:< Dog]
    }

    /**
     * EXERCISE 2
     * 
     * Invariance means there is no relationship.
     * 
     * Uncomment following code and note which statements the compiler is able to proove.
     */
    object invariantProofs {
      // val subtypeProof   = implicitly[Invariant[Dog] <:< Invariant[Animal]]
      // val supertypeProof = implicitly[Invariant[Animal] <:< Invariant[Dog]]
    }

    /**
     * EXERCISE 3
     * 
     * Covariance means the relationship is preserved.
     * 
     * Uncomment following code and note which statements the compiler is able to proove.
     */
    object covariantProofs {
      // val subtypeProof   = implicitly[Covariant[Dog] <:< Covariant[Animal]]
      // val supertypeProof = implicitly[Covariant[Animal] <:< Covariant[Dog]]
    }

    /**
     * EXERCISE 4
     * 
     * Contravariance means the relationship is inversed.
     * 
     * Uncomment following code and note which statements the compiler is able to proove.
     */
    object contravariantProofs {
      // val subtypeProof   = implicitly[Contravariant[Dog] <:< Contravariant[Animal]]
      // val supertypeProof = implicitly[Contravariant[Animal] <:< Contravariant[Dog]]
    }
  }

  /**
   * With product and sum types we can represent problems with data and then using
   * pattern matching interpret that data.
   * 
   * Combined with generic types we can model any problem in a nice, type-safe way.
   */
  package algebraicDataTypes {

    import java.time.YearMonth

    sealed trait PaymentMethod
    case class CreditCard(number: String, expires: YearMonth) extends PaymentMethod
    case class PaypalAccount(username: String)                extends PaymentMethod

    object PaymentExample extends App {
      def creditCardService(amount: Int, cardNumber: String): Unit = println(s"Payed $amount euro using credit card $cardNumber")
      def paypalService(amount: Int, username: String): Unit       = println(s"Payed $amount euro using paypal account $username")

      def pay(amount: Int, method: PaymentMethod) =
        method match {
          case CreditCard(number, _)   => creditCardService(amount, number)
          case PaypalAccount(username) => paypalService(amount, username)
        }

      pay(42, PaypalAccount("foo123"))
    }

    /**
     * Using generic types, variance and algebraic data types we can construct our own data type
     * to represent absence of a value.
     * 
     * This is the simplified equivalent of Scala's built-in `Option` type.
     */
    sealed trait Maybe[+A] { self =>

      def map[B](f: A => B): Maybe[B] =
        self match {
          case Missing    => Missing
          case Present(a) => Present(f(a))
        }

      def flatMap[B](f: A => Maybe[B]) =
        self match {
          case Missing    => Missing
          case Present(a) => f(a)
        }
    }

    case object Missing             extends Maybe[Nothing]
    case class Present[A](value: A) extends Maybe[A]

    object Maybe {

      def empty[A]: Maybe[A] = Missing

      def pure[A](value: A): Maybe[A] = Present(value)

      def cond[A](test: Boolean, value: A): Maybe[A] =
        if (test) pure(value)
        else      empty
    }

    /**
     * EXERCISE 5
     * 
     * Refactor `validate` method to use for-comprehension.
     */
    object MaybeExample extends App {
      def notEmpty(p: String) : Maybe[String] = Maybe.cond(p.nonEmpty, p)
      def minLength(p: String): Maybe[String] = Maybe.cond(p.length > 10, p)
      def maxLength(p: String): Maybe[String] = Maybe.cond(p.length < 32, p)
      def hasLower(p: String) : Maybe[String] = Maybe.cond(p.toUpperCase != p, p)
      def hasUpper(p: String) : Maybe[String] = Maybe.cond(p.toLowerCase != p, p)

      def validate(password: String): Maybe[String] =
        notEmpty(password)
          .flatMap(minLength)
          .flatMap(maxLength)
          .flatMap(hasLower)
          .flatMap(hasUpper)

      val validated = validate("fooBARbaz47")
      println(validated)
    }
  }
}

/**
 * As a graduation project you will build a custom `Either` data type, representing a binary
 * disjunction.
 */
package graduation {

  /**
   * EXERCISE 1
   * 
   * Add all the cases to this data type.
   * 
   * Note: consider if you need variance for A and B type parameters.
   */
  sealed trait Choice[A, B] {

    /**
     * EXERCISE 2
     * 
     * Implement `map` method for choice.
     */
    def map[C](f: B => C): Choice[A, C] = ???

    /**
     * EXERCISE 3
     * 
     * Implement `flatMap` method for choice.
     */
    def flatMap[C](f: B => Choice[A, C]): Choice[A, C] = ???

    /**
     * EXERCISE 4
     * 
     * Implement `fold` method for choice.
     */
    def fold[C](fa: A => C, fb: B => C): C = ???

    /**
     * EXERCISE 5
     * 
     * Implement `swap` method for choice.
     */
    def swap: Choice[B, A] = ???
  }

  object Choice {
    
    /**
     * EXERCISE 6
     * 
     * Construct the first choice case.
     */
    def first[A, B](value: A): Choice[A, B] = ???
    
    /**
     * EXERCISE 7
     * 
     * Construct the second choice case.
     */
    def second[A, B](value: B): Choice[A, B] = ???
    
    /**
     * EXERCISE 8
     * 
     * Create a method that encodes JSON value into JSON string.
     */
    def cond[A, B](test: Boolean, right: => B, left: => A): Choice[A, B] = ???
  }
}