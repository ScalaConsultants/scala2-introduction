package io.scalac.scala2

/**
 * INTRODUCTION
 * 
 * Scala stands for "Scalable Language" and its true in both that Scala is flexible
 * and scales to your needs, as well as it's powerfull enough to solve the hardest problems.
 * 
 * Since it's inception in 2003 it's been the backbone of many popular platforms and
 * frameworks such as Spark, Kafka, Akka, Play, Cats and most recent ZIO.
 * 
 * Scala is a high-level programming language with robust type system and support for
 * generic programming. It's static type system helps to write complex applications in
 * a consise and precise fashion allowing to catch problems at compile time.
 * 
 * Scala is a generic-purpose programming language and supports JVM and JS runtimes.
 * Excellent inteop with both Java and JavaScript allows for easy access to their huge
 * ecosystems of libraries.
 */

/**
 * To create a runnable Scala application by we need to expose a statically available `main`
 * method.
 */
package running {

  /**
   * The canonical way of defining an application.
   */
  object CanonicalApp {

    def main(args: Array[String]): Unit = println("Hello world!")
  }

  /**
   * The shorthand way of defining an application.
   */
  object ShorthandApp extends App {
    println("The body of the object becomes the body of the main method.")
    println("[Note] We still have access to args value: " + args.getClass.getTypeName)
  }
}

/**
 * Scala 2 syntax was designed to be concise and powerful. It allows you to express a range of
 * concepts from very concrete like value literals to very abstract such as generic methods.
 */
package syntax {
  
  import scala.annotation.nowarn

  @nowarn("msg=pure expression does nothing")
  object intro {
    
    /**
     * Primitive expressions represent the simplest elements.
     */
    "Welcome!"
    42
    true

    /**
     * You can combine primitive expressions using operators. At runtime, these expressions
     * will be evaluated and reduced to a single value.
     */
    "Welcome" + " again!"
    40 + 2
    true || false

    /**
     * The result of an expression can be assigned an identifier, by which we can refer to it other
     * expressions. Values are immutable constructs and cannot be reassigned.
     * 
     * Hover over the identifier and note what type is inferred. 
     */
    val hello = "Hello" + " world!"
    val four  = 1 + 3

    /**
     * Identifiers, types and values.
     */
    val title      : String  = "Welcome to Scala 2 workshop!"
    val days       : Int     = 2
    val isExciting : Boolean = true

    /**
     * Identifiers have to start with a letter and cannot be reserved keywords.
     * 
     * You can use backticks to overcome these limitations, although it's better to just find
     * another name instead, as this is awkward to type.
     */
    val `?`     : String = "Mr Riddle"
    val `class` : String = "Trickster"

    // multiline strings
    val flightAnnouncement: String =
      """Ladies and gentlemen, the Coach has turned on the Enter Focus Mode sign. If you haven't
        |already done so, please clone the repository and open it in your favorite IDE. Please
        |compile the project and relax.
        |
        |If you are experiencing any issues, please read the README.md file in the root directory.
        |If you require further assistance, contact me during the first coffee break.
        |
        |If you have any questions about the material, please don't hestitate to ask on chat. Even
        |basic questions can spark interesting discussions.""".stripMargin

    // string interpolation
    val plan: String =
      s"Over the next $days days you will learn the fundamentals of Scala programming."

    /**
     * Multiline expressions must be wrapped in curly braces to mark the beggining and end of the
     * code block.
     */
    val helloMessage: String = {
      val name    = "Peter"
      val surname = "Parker"

      s"Hello $name $surname!" // last value is returned
    }

    /**
     * Methods are defined by their identifier, parameter list and return type. The body of the
     * method is an expression (code block) whos scope has access to all parameters.
     * 
     * Just like every code block
     */
    def personalizedHello(name: String): String = {
      val welcome = s"Hi $name!"
      val message = s"$welcome $plan"
      
      message 
    }

    /**
     * Methods can have multiple arguments
     */
    def sum3(a: Int, b: Int, c: Int): Int = a + b + c

    /**
     * We can make arguments optional by providing default values
     */
    def log(message: String, level: String = "INFO"): Unit = println(s"[$level] $message")

    def info(message: String) : Unit = log(message)
    def error(message: String): Unit = log(message, "ERROR")

    /**
     * Methods can have varying amount of arguments. The repeatable argument must be the last one.
     */
    def sumAll(head: Int, tail: Int*): Int = ???

    val sum4   = sumAll(1, 2, 3, 4)
    val sum5   = sumAll(1, 2, 3, 4, 5)
    val sum6   = sumAll(1, 2, 3, 4, 5, 6)
    val sum100 = sumAll(
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
      1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25
    )

    // tuples
    val point  : (Int, Int)      = (0, 0)
    val vector : (Int, Int, Int) = (0, 1, 2)

    // shorthand syntax for 2-tuples
    val person : (String, Int)   = "Jack" -> 17

    /**
     * Methods can contain nested method definitions. These nested methods are not visible outside
     * of the scope they were defined in.
     * 
     * A method always returns a single value. If we want to return more, we can return a tuple.
     */
    def divisionWithRemainder(dividend: Int, divisor: Int): (Int, Int) = {
      def quotient : Int = dividend / divisor
      def remainder: Int = dividend % divisor

      (quotient, remainder)
    }
  }

  object exercises {

    /**
     * EXERCISE 1
     * 
     * Assign a value to identifier `name`.
     */
    val name: String = ???

    /**
     * EXERCISE 2
     * 
     * Assign a value to identifier `javaExperience`.
     */
    val javaExpierience: Int = ???

    /**
     * EXERCISE 3
     * 
     * Simplify the following nested expression.
     */
    val message: String = {
      val first: String = "Alice"
      val res1: String = {
        val second: String = "in"
        val res2: String = {
          val third: String = "Wonderland"
          s"$first $second $third"
        }
        res2
      }
      res1
    }

    /**
     * EXERCISE 4
     * 
     * Refactor using string iterpolation.
     */
    val greeting: String =
      "Hi! My name is" + name + ". I've been doing Java for " + javaExpierience + " years."

    /**
     * EXERCISE 5
     * 
     * Hover over user value to inspect its type and note your findings.
     */
    val user = 1 -> "jim" -> "jim@acme.com"

    /**
     * EXERCISE 6
     * 
     * Refactor this method body so that it does not utilize curly braces.
     */
    def translatePoint(x: Int, y: Int, dx: Int, dy: Int): (Int, Int) = {
      val tx = x + dx
      val ty = y + dy
      (tx, ty)
    }
  }

  /**
   * Lazy values allow to delay the initialization of a value until its first access.
   * 
   * To guarantee the value is initialized only once the implementation uses a lock on the object.
   * This can potentially cause a deadlock if we have two objects refering to each other in a
   * concurrent program.
   */
  object LazyVals extends App {

    lazy val delayedInit: String = {
      val value = "<foo>"
      println("Initializing `delayedInit`")
      value
    }

    println("-- Start --")
    println(s"The value is $delayedInit")
    println(s"The value is still $delayedInit")
    println("-- End --")
  }

  /**
   * Lazy values may be used to decouple the declaration of values from their dependencies.
   * 
   * This is an anti-pattern that can lead to non-obvious errors.
   */
  object LazyDepsObject extends App {

    /**
     * Experiment with `println` at diffrent positons. Note your findings.
     */
    lazy val foobar: String = s"$foo $bar"

    // println(foobar)

    val foo: String = "foo"
    val bar: String = "bar"

    // println(foobar)
  }

  object LazyDepsPrimitive extends App {

    /**
     * Experiment with `println` at diffrent positons. Note your findings.
     */
    lazy val boolValue : String = s"$boolA $boolB"
    lazy val intValue  : String = s"$intA $intB"

    // println(boolValue)
    // println(intValue)

    val boolA : Boolean = true
    val boolB : Boolean = false
    val intA  : Int = 1
    val intB  : Int = 2

    // println(boolValue)
    // println(intValue)
  }

  object Imports extends App {
    // import by name
    import intro.{title, plan}

    println(title)
    println("---")
    println(plan)
    println("---")

    // wildcard import
    import intro._

    println(flightAnnouncement)
  }
}

/**
 * Template definitions allow you to group values, methods and types.
 */
package templateDefinitions {

  object intro {

    /**
     * Classes are templates for creating objects. They encapsulate values, methods and types.
     */
    class Person(name: String, surname: String, age: Int) {

      val fullName: String = s"$name $surname"

      def casualIntro(): Unit = println(s"Hi! I'm $name!")
      def formalIntro(): Unit = println(s"Welcome. My name is $fullName.")
    }

    /**
     * To construct an instance of a class we use the `new` keyword.
     * 
     * Hover over the value and note what type is inferred.
     */
    val max  = new Person("Max", "Payne", 34)
    val lara = new Person("Lara", "Croft", 21)

    /**
     * Objects are singleton instances used to hold constants and statically available methods.
     */
    object Duck {
      def quack(): Unit = println("Quack! Quack!")
    }

    /**
     * Hover over the value to inspect inferred type. Note your findings.
     */
    val duck = Duck

    /**
     * Traits are used to share members between classes. They are similar to Java interfaces.
     */
    trait Accounts {
      def getBalance(accountId: String): Double
      def setBalance(accountId: String, balance: Double): Unit
    }

    trait PaymentService {
      def pay(from: String, to: String, amount: Double): Boolean
    }

    /**
     * Classes and objects can extend traits.
     */
    class PaymentServiceLive(accounts: Accounts) extends PaymentService {

      def pay(from: String, to: String, amount: Double) = ???
    }
  }

  object exercises {

    /**
     * EXERCISE 1
     * 
     * Add `multiply` and `subtract` methods to the calculator.
     */
    object Calculator {

      def add(a: Int, b: Int): Int = a + b
    }

    /**
     * EXERCISE 2
     * 
     * Construct updated counter using the `new` keyword.
     */
    class Counter(state: Int) {

      def increment(): Counter = ???
    }

    /**
     * EXERCISE 3
     * 
     * Use the `extends` and `with` keywords to mixin `CanBark` and `CanHowl` behavior.
     */
    class Dog

    trait CanBark {
      def bark(): Unit = println("Woof! Woof!")
    }

    trait CanHowl {
      def howl(): Unit = println("Owooooo!")
    }
    
    /**
     * EXERCISE 4
     * 
     * Try accessing the name and age of the `Cat`. Note your findings.
     */
    class Cat(val name: String, age: Int)

    val cat  : Cat    = new Cat("Tom", 2)
    val name : String = ???
    val age  : Int    = ???
  }
}

/**
 * Scala provides operators for branching and iteration flow control.
 */
package controlFlow {

  object examples {

    val isWelcome: Boolean = true

    /**
     * Conditional operator
     */
    val defaultWelcome: String =
      if (isWelcome) "Welcome!"
      else           "You're not welcome here."

    val name: String = "Jake"

    /**
     * Conditionals can be chained
     */
    val welcomeConditional: String =
      if (name == "John")      "Welcome John!"
      else if (name == "Jim")  "Welcome Jim!"
      else if (name == "Jake") "Welcome Jake!"
      else                     defaultWelcome

    /**
     * Basic pattern matching syntax
     */
    val welcomeExactMatch: String =
      name match {
        case "John" => "Welcome John!"
        case "Jim"  => "Welcome Jim!"
        case "Jake" => "Welcome Jake!"
        case _      => defaultWelcome
      }

    /**
     * Pattern matching variabled and guards
     */
    val welcomeMatchGuard: String =
      name match {
        case name if name == "John" || name == "Jim" || name == "Jake" => s"Welcome $name!"
        case _                                                         => defaultWelcome
      }

    /**
     * Pattern matching with regular expressions
     */
    import scala.util.matching.Regex

    val namePattern: Regex = "^(John|Jim|Jake)$".r
    val welcomeMatchRegex: String =
      name match {
        case namePattern(x) => s"Welcome $x!"
        case _              => defaultWelcome
      }

    /**
     * Pattern matching on types
     */
    trait Shape
    class Circle(val radius: Double)
    class Square(val side: Double)

    def area(shape: Shape): Double =
      shape match {
        case c: Circle => 3.14 * c.radius * c.radius
        case s: Square => s.side * s.side
        case _         => 0
      }

    /**
     * Under the hood Scala is using `isInstanceOf` check followed by `asInstanceOf` cast.
     * The following is the desugared equivalent.
     */
    def area2(shape: Shape): Double =
      shape match {
        case x if x.isInstanceOf[Circle] =>
          val c: Circle = x.asInstanceOf[Circle]
          3.14 * c.radius * c.radius
        case x if x.isInstanceOf[Square] =>
          val s: Square = x.asInstanceOf[Square]
          s.side * s.side
        case _ => 0
      }
  }

  object exercises {

    /**
     * EXERCISE 1
     * 
     * Calculate ticket price depending on age group (kids, teenagers, adults, seniors).
     * Apply a 50% discount, if it's their birthsday.
     */
    def ticketPrice(age: Int, isBirthsday: Boolean): Double = ???

    /**
     * EXERCISE 2
     * 
     * Summarize the weather in one word, such as `sunny`, `rainy`, `chilly` or `snowing`, depending
     * on weather conditions.
     */
    def weatherSummary(temperature: Double, isRaining: Boolean): String = ???

    /**
     * EXERCISE 3
     * 
     * Extract username (the part before @) from an email address using regular expressions.
     * Default to empty string, if pattern is not found.
     */
    def username(email: String): String = ???

    /**
     * EXERCISE 4
     * 
     * Extract server (the part after @) from an email address using regular expressions.
     * Default to empty string, if pattern is not found.
     */
    def server(email: String): String = ???

    /**
     * EXERCISE 5
     * 
     * See if you can spot a bug in this method and refactor it to fix it. Note your findings.
     */
    trait Animal
    class Cat(val name: String)   extends Animal
    class Dog(val name: String)   extends Animal
    class Duck(val color: String) extends Animal
    class Cow                     extends Animal

    def show(animal: Animal): String =
      animal match {
        case x if x.isInstanceOf[Cat]  => s"It's a cat named ${x.asInstanceOf[Cat].name}"
        case x if x.isInstanceOf[Dog]  => s"It's a dog named ${x.asInstanceOf[Cat].name}"
        case x if x.isInstanceOf[Duck] => s"It's a ${x.asInstanceOf[Duck].color} duck"
        case x                         => s"It's just a cow"
      }
  }
}

/**
 * Scala allows you to call `apply` methods without explicitly typing their name. This feature
 * enables more readable API's and will be important later when we discuss function encoding.
 */
package construction {

  class Multiplicator(factor: Int) {
    def apply(number: Int): Int = number * factor
  }

  object MultiplicatorExample extends App {
    val doubler = new Multiplicator(2)
    val tripler = new Multiplicator(3)

    println(doubler.apply(5))
    println(tripler(5))
  }

  class Cat(name: String, age: Int) {
    override def toString(): String = s"A $age year old cat named $name."
  }

  object CatUtil {
    def apply(name: String, age: Int): Cat = new Cat(name, age)
  }

  object CatsExample extends App {
    val luna    = CatUtil.apply("Luna", 5)
    val mercury = CatUtil("Mercury", 7)
    println(luna)
    println(mercury)
  }

  object exercises {

    /**
     * EXERCISE 1
     * 
     * An object that is declared in the same file as a class and has the same name is called a
     * "Companion Object".
     * 
     * Introduce changes to companion object such that you can drop the `new` keyword in the
     * following code.
     */
    class Dog(name: String, age: Int)
    object Dog

    val rex: Dog = new Dog("Rex", 2)

    /**
     * EXERCISE 2
     * 
     * Refactor the following code such that the `new` keywords can be dropped.
     */
    class Notepad(notes: String*)
    object Notepad

    val notes1: Notepad = new Notepad("one")
    val notes2: Notepad = new Notepad("one", "two")
    val notes3: Notepad = new Notepad("one", "two", "three")
  }
}

/**
 * Scala has a special pattern called "Extractor Objects" for deconstructing complex values.
 * 
 * To define an extractor object it needs a method `unapply` or `unapplySeq` that takes a single
 * value and returns an optional decomposed value.
 */
package deconstruction {

  class Cat(val name: String, val age: Int)

  object CatUtil {
    def unapply(cat: Cat): Option[(String, Int)] = Some((cat.name, cat.age))
  }

  object CatExample extends App {
    val luna = new Cat("Luna", 5)

    val CatUtil(name, age) = luna
    println(name)
    println(age)
  }

  import java.time.LocalDateTime

  object Date {
    def unapplySeq(dt: LocalDateTime): Option[Seq[Int]] = Some(Seq(dt.getYear, dt.getMonthValue, dt.getDayOfMonth))
  }

  object Time {
    def unapplySeq(dt: LocalDateTime): Option[Seq[Int]] = Some(Seq(dt.getHour, dt.getMinute, dt.getSecond))
  }

  object DateTimeExample extends App {
    val now = LocalDateTime.now

    val Date(year, month, day) = now
    println(s"$year/$month/$day")

    val Time(hour, minutes, _) = now
    println(s"$hour:$minutes")
  }

  /**
   * Extractor objects are primarily meant to be used in pattern matching.
   */
  object ExtractorsInPatternMatching extends App {

    val now = LocalDateTime.of(2020, 7, 30, 4, 50)

    now match {
      case Date(2020, m, _) if m < 3 => println("Covid is coming...")
      case Date(2020, 7, 30)         => println("Perseverance launch day!")
      case _                         => println("Nothing interesting")
    }
  }
  
  /**
   * Class instances can also be extractor objects. Scala's built in scala.util.matching.Regex is
   * powered by this feature.
   */
  class CaseInsensitiveMatch(expected: String) {
    def unapply(actual: String): Option[String] =
      if (expected.toLowerCase == actual.toLowerCase) Some(actual)
      else                                            None
  }

  object CaseInsensitiveMatchExample extends App {
    val john = new CaseInsensitiveMatch("JOHN")
    val jim  = new CaseInsensitiveMatch("JIM")

    "Jim" match {
      case john(v) => println(s"Matched $v!")
      case jim(v)  => println(s"Matched $v!")
      case _       => println("Not matched")
    }
  }

  object exercises {

    val point3d: (Int, Int, Int) = (5, 7, 9)

    val person: (String, Int) = ("Jim", 60)
    
    /**
     * EXERCISE 1
     * 
     * Destructure the point3d tuple into its components.
     */
    val (x, y, z): (Int, Int, Int) = ???
    
    /**
     * EXERCISE 2
     * 
     * Destructure the person tuple into its components.
     */
    val (name, age): (String, Int) = ???
    
    /**
     * EXERCISE 3
     * 
     * Create an extractor object for the following class.
     */
    class FullName(val name: String, val surname: String)
    
    /**
     * EXERCISE 4
     * 
     * Create an extractor object for the following class.
     */
    class Notepad(notes: String*)
    object Notepad

    /**
     * EXERCISE 5
     * 
     * Use extractor from previous exercise to extract first three notes and print them.
     */
    def printFirst3(notepad: Notepad): Unit = 
      notepad match {
        // case ??? => ???
        case _ => println("This notepad contains less than 3 notes.")
      }
  }
}