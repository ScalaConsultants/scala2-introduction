package io.scalac.scala2

/**
 * In Scala functions are first-class citizens. Functions are encoded as classes with a single
 * `apply` method. This allows functions to be passed around as values at the cost of additional
 * memory allocations (the function is an object).
 */
package functions {

  object definition {

    def addMethod(a: Int, b: Int): Int = a + b

    /**
     * Hover over the value and see what type is inferred for this function.
     */
    val inferredFn = (a: Int, b: Int) => a + b

    /**
     * The brace syntax is shorthand for FunctionN classes up to 22 arity.
     */
    val functionClass: Function2[Int, Int, Int] = inferredFn

    /**
     * There is a one-to-one correspondence between the type signature and implementation,.
     */
    val addition
      : (Int, Int) => Int
      = (a, b)     => a + b

    /**
     * EXERCISE 1
     * 
     * Define integer subtraction binary function.
     */
    val subtraction = ???

    /**
     * EXERCISE 2
     * 
     * Define integer multiplication binary function.
     */
    val multiplication = ???

    /**
     * Functions are just regular values that can be passed around.
     */
    def calculate(a: Int, b: Int, operation: (Int, Int) => Int): Int =
      operation(a, b)

    /**
     * EXERCISE 3
     * 
     * Use the calculate method to add five to ten.
     */
    val fivePlusTen: Int = ???

    /**
     * EXERCISE 4
     * 
     * Use the calculate method to multiply three by six.
     */
    val threeTimesSix: Int = ???

    /**
     * Scala allows you to supply a method where isomorphic function is required.
     * Under the hood Scala will create an anonymous function delegating to given method.
     */
    val twoPlusEight: Int = calculate(2, 8, addMethod)

    /**
     * You don't have to name a function. If it's only used in one place, you can provide an
     * anonymous function (so called lambda) as an argument.
     */
    val sixMinusTwo: Int = calculate(6, 2, (a: Int, b: Int) => a - b)

    /**
     * You can use underscore to sequentially refer to parameters of a function. The compiler knows
     * everything about the shape and types of the function from `calculate` definition, so we can
     * use shorthand syntax to supply an anonymous function.
     */
    val sixPlusTwo: Int = calculate(6, 2, _ + _)

    /**
     * You can use the shorthand whenever the compiler has enough information to infer the types.
     * In this example the explicit type ascription tells the compiler everything it needs to know.
     */
    val modulo: (Int, Int) => Int = _ % _

    /**
     * Underscore can also be used to partially apply a function. In this example we're turning a
     * function that takes two parameters into a function that takes just one, by fixing the first
     * parameter.
     */
    val multiply : (Int, Int) => Int = (a, b) => a * b
    val double   : Int        => Int = multiply(2, _)

    /**
     * EXERCISE 5
     * 
     * Implement the combine method, which takes two string values and a binary operation and
     * returns a string.
     */
    def combine = ???

    /**
     * EXERCISE 6
     * 
     * Use the `combine` method with an anonymous function to construct the full name.
     */
    val firstName : String = "Ada"
    val lastName  : String = "Lovelace"
    val fullName  : String = ???

    /**
     * The following functions are perform the same job, but their signature is diffrent.
     * 
     * To convert from one to another we can use utility methods defined in Function object.
     */
    val addPair  : (Int, Int)   => Int = (a, b) => a + b
    val addTuple : ((Int, Int)) => Int = t      => t._1 + t._2

    import Function.{tupled, untupled}

    val addPair2  : (Int, Int)   => Int = untupled(addTuple)
    val addTuple2 : ((Int, Int)) => Int = tupled(addPair)

    /**
     * EXERCISE 7
     * 
     * Replace addPair with addTuple. Note your findings.
     */
    val sevenPlusOne: Int = calculate(7, 1, addPair)
  }
}

/**
 * Currying is a process that transforms a function that takes multiple arguments into a sequence
 * of functions that each take a single argument.
 */
package currying {

  object example {

    /**
     * Let's consider this function that takes 3 arguments and returns their sum.
     */
    val sum
      : (Int, Int, Int) => Int
      = (a, b, c) => a + b + c

    /**
     * We can rewrite it into a function that takes 2 arguments, and returns another function that
     * takes a single argument and finally returns the sum of all three.
     */
    val sumABandC
      : (Int, Int) => (Int => Int)
      = (a, b)     => (c   => a + b + c)

    /**
     * We can repeat this process until we end up a sequence of single argument functions each
     * returning another single argument function, except the last one, which returns the final
     * result.
     */
    val sumAandBandC
      : Int => (Int => (Int => Int))
      = a   => (b   => (c   => a + b + c))
      
    /**
     * The final result is the `curried` form of the same function. Also we don't need parenthesis.
     */
    val curriedSum
      : Int => Int => Int => Int
      = a   => b   => c   => a + b + c

    val regularCall : Int = sum(1, 2, 3)
    val curriedCall : Int = curriedSum(1)(2)(3)

    /**
     * EXERCISE 1
     * 
     * Refactor the following function into its curried form.
     */
    val greet
      : (String, String, Int)      => String
      = (firstName, lastName, age) =>
        if (age > 18) s"Welcome $firstName $lastName"
        else          s"Hi $firstName"

    val greetCurried = ???

    /**
     * EXERCISE 2
     * 
     * The reverse process is called `uncurrying`. Refactor the following function into its
     * uncurried form.
     */
    val multiply
      : Int => Int => Int => Int
      = a   => b   => c   => a * b * c

    val uncurriedMultiply = ???

    /**
     * In Scala functions and methods are similar and one can easily convert from one to the other.
     * The same process can be applied to methods.
     */
    def fullAddress(county: String, city: String, street: String): String =
      s"$street, $city, $county"

    val fullAddressExample: String =
      fullAddress("Co. Dublin", "Dublin", "O'Connell Street")

    /**
     * We could define an intermediate class to curry this method. Using the `apply` name we can
     * achieve similar syntax at call site.
     */
    class CountyCity(county: String, city: String) {
      def apply(street: String): String =
        s"$street, $city, $county"
    }

    def countyCity(county: String, city: String): CountyCity =
      new CountyCity(county, city)

    val countyCityExample: String =
      countyCity("Co. Dublin", "Dublin")("O'Connell Street")

    /**
     * We can define more intermediate classes until we reach fully curried syntax.
     */
    class County(county: String) {
      def apply(city: String): CountyCity =
        new CountyCity(county, city)
    }

    def county(county: String): County =
      new County(county)

    val countyExample: String =
      county("Co. Dublin")("Dublin")("O'Connell Street")

    /**
     * Although this technique is very useful it is also somewhat repetitive and requires writing a
     * lot of boilerplate. However we can use another feature of Scala that has been designed
     * specifically to address currying - multiple parameter lists.
     */
    def fullAddressCurried(county: String)(city: String)(street: String): String =
      s"$street, $city, $county"

    val fullAddressCurriedExample: String =
      fullAddressCurried("Co. Dublin")("Dublin")("O'Connell Street")
  }
}

/**
 * Higher order function is a function that
 *   - takes one or more functions as an argument
 *   - returns a funciton as a result
 *   - or both.
 * 
 * We've seen some higher order functions examples above.
 */
object higherOrderFunctionExamples {

  /**
   * Takes another function as an argument.
   */
  val logPretty
    : (String, String => String) => Unit
    = (message, prettify)        => println(prettify(message))

  /**
   * Returns a function.
   * 
   * Notice: every function of at least 2 arguments in its curried form will be of higher order.
   */
  val curriedAdd
    : Int => (Int => Int)
    = a   => (b   => (a + b))

  /**
   * Both takes and returns functions.
   */
  val ticketPrice
    : (Int => String, String => Double) => (Int => Double)
    = (groupByAge,    priceByGroup)     => (age  => (groupByAge andThen priceByGroup)(age))

}

/**
 * Methods will be automatically converted into functions.
 */
object MethodConversionExample extends App {
  
  def draw(value: Int, render: Int => String): Unit =
    println(render(value))

  def line(size: Int): String = "-".repeat(size)
  def square(size: Int): String = (
    for (n <- 1 to size)
    yield s"$n: " + "*".repeat(size)
  ).mkString("\n")

  println("draw line")
  draw(5, line)

  println("\ndraw square")
  draw(5, square)
}

/**
 * Functions and methods will have diffrent properties depending on their implementation. Some of
 * these properties are desired characteristics, as they help us to avoid common mistakes and
 * guide us towards cleaner and more testable code.
 * 
 * A total function is a function that handles all its inputs. A mathematical function is total by
 * definition, however that is not true for all functions in Scala. It's dual - partial functions
 * allow to define useful methods to compute values (like division) using exceptions as the
 * escape hatch mechanism.
 * 
 * A deterministic function is a function that for the same input, always returns the same output.
 * Non-deterministic functions are harder to test.
 * 
 * A referentially transparent expression is one that can be safely replaced by its value and that
 * will not change programs behaviour. For a function to be referentially transparent it must only
 * depend on its inputs, without accessing any shared mutable state or interacting with outside
 * world (side-effects).
 * 
 * A pure function is a function that is total, deterministic and referentially transparent.
 * Pure functions are easy to test, as they can be treated like black boxes. To test you simply
 * feed it some input and make assertions on the output.
 */
package characteristics {

  object examples {

    val pureAdd
      : (Int, Int) => Int
      = (a, b)     => a + b

    // not referentially transparent
    val impureAdd
      : (Int, Int) => Int
      = (a, b)     => {
        println(s"Adding $a and $b")
        a + b
      }

    val pureDivide
      : (Int, Int) => Option[Int]
      = (a, b)     =>
        if (b == 0) None
        else        Some(a / b)

    // not total, will throw java.lang.ArithmeticException if the divisor is zero
    val impureDivide
      : (Int, Int) => Int
      = (a, b)     => a / b

    import java.time.LocalTime

    val pureTime
      : Long    => LocalTime
      = seconds => LocalTime.ofSecondOfDay(seconds)

    // non-deterministic
    val impureTime
      : () => LocalTime
      = () => LocalTime.now()
  }

  object exercises {

    val partialGreet: PartialFunction[String, String] =
      (name: String) => name match {
        case "Jim" | "Jack" | "John" => s"Hello $name!"
      }

    /**
     * EXERCISE 1
     * 
     * Use the `applyOrElse` method to turn a partial function into a total function.
     */
    val totalGreet: String => String = partialGreet

    /**
     * EXERCISE 2
     * 
     * Refactor the following method to make it deterministic.
     */
    var currentYear: Int = 2021
    def yearAfter(years: Int): Int = currentYear + years

    /**
     * EXERCISE 3
     * 
     * Analyse the following method. Is it pure? Why? Note your findings.
     */
    val pi: Double = 3.14
    def circleArea(radius: Double): Double = pi * radius * radius

    /**
     * EXERCISE 4
     * 
     * Analyse the following method. Is it pure? Why? Note your findings.
     */
    def fibonacci(n: Int): Long = {
      if (n < 0) ???
      else if (n == 0) 0
      else if (n <= 2) 1
      else {
        var (left, right, i) = (0, 1, 2)

        def calculateNextSum() = {
          val sum = left + right
          if (i % 2 == 0) left  = sum
          else            right = sum
        }

        while (i < n) {
          calculateNextSum()
          i = i + 1
        }
        left + right
      }
    }
  }
  
  /**
   * EXERCISE 5
   * 
   * Run the following application. Then replace f with `partialGreet` and run again.
   * Note your findings.
   */
  object PartialFunctionExample extends App {
    import exercises._

    val f: String => String = totalGreet
    val greeting = f("Jimmy")
    println(greeting)
  }
}

/**
 * Functions can be composed to return another function which is equivalent to applying the
 * individual functions sequentially.
 */
package composition {
  
  object examples {

    val inc    : Int => Int = _ + 1
    val triple : Int => Int = _ * 3

    // andThen applies operations from left to right
    val tripleInc
      : Int => Int 
      = n   => (triple andThen inc)(n)

    // the same can be achieved by regular function application
    val tripleIncApply
      : Int => Int
      = n   => inc(triple(n))

    // compose applies operations from right to left
    val incTriple
      : Int => Int 
      = n   => (triple compose inc)(n)

    // is the equivalent of
    val incTripleApply
      : Int => Int
      = n   => triple(inc(n))

    /**
     * Chain allows us to combine a sequence of operations using `andThen`, with the limitation
     * that the input and output type of all operations must be the same.
     */
    import Function.chain

    val ops: Seq[Int => Int] = Seq(inc, inc, triple)

    val incIncTriple
      : Int => Int
      = chain(ops)

    // is the equivalent of
    val incIncTripleApply
      : Int => Int
      = n   => inc(inc(triple(n)))

    /**
     * To mimic combinng with `compose`, we can reverse the sequence.
     */
    val tripleIncInc
      : Int => Int
      = chain(ops.reverse)

    // is the equivalent of
    val tripleIncIncApply
      : Int => Int
      = n => triple(inc(inc(n)))
  }

  object CompositionExample extends App {
    import examples._

    println(
      s"""(triple compose inc)(2) = ${tripleInc(2)}
         |(triple andThen inc)(2) = ${incTriple(2)}
         |
         |chain(ops)(2)           = ${incIncTriple(2)}
         |chain(ops.reverse)(2)   = ${tripleIncInc(2)}""".stripMargin
    )
  }

  object exercises {

    import Function._

    /**
     * EXERCISE 1
     * 
     * Compose prettyPrint after sum function.
     * 
     * Hint: you will need `tupled` conversion to align the types.
     */
    val sum
      : (Int, Int) => Int
      = (a, b)     => a + b

    val prettyPrint
      : Int => String
      = n   => s"Result: $n"

    val `prettyPrint º sum`
      : (Int, Int) => String
      = ???

    /**
     * EXERCISE 2
     * 
     * Compose prettify and consoleLog methods by regular method application.
     */
    def prettify(severity: String, message: String): String = s"[$severity] $message"
    def consoleLog(message: String): Unit = println(message)

    def log(severity: String, message: String): Unit = ???

    /**
     * EXERCISE 3
     * 
     * Chain following transformations into a function that represents data cleaning pipeline.
     */
    val trimWhitespacePrefix : String => String = _.stripLeading
    val trimWhitespaceSuffix : String => String = _.stripTrailing
    val takeLastChunk        : String => String = _.split(" ").lastOption.getOrElse("")
    val removeNonAlphabetic  : String => String = _.replaceAll("[^a-zA-Z]", "")

    val cleanData: String => String = ???
    val testRow: String = "  Lorem ipsum 47 dolor sit amet17.   "
  }
}

/**
 * By default Scala eagerly evaluates all parameters before evaluating the body of a method or
 * function. This strategy is called "call by value".
 * 
 * Using the "call by value" syntax we can opt-in to delay the evaluation to use site.
 */
package evaluation {
  
  import scala.annotation.nowarn
  import scala.util.Random

  object examples extends App {

    @nowarn("msg=pure expression does nothing")
    def callByValue(thunk: Unit): Unit = {
      thunk
      thunk
    }

    def callByName(thunk: => Unit): Unit = {
      thunk
      thunk
    }

    callByValue(println("Foo"))
    callByName(println("Bar"))
  }

  object exercises {

    object LogLevel {
      val INFO  = 1
      val ERROR = 2
      val DEBUG = 3
    }

    object Logging {
      val LEVEL = LogLevel.ERROR
    }

    /**
     * EXERCISE 1
     * 
     * Assume parameters evaluation is a potentially expensive operation.
     * Log the message only if it meets `Logging.LEVEL` threshold.
     */
    def log(level: Int, message: String): Unit = ???

    /**
     * EXERCISE 2
     * 
     * Assume parameters evaluation is a potentially expensive operation.
     * Exchange rate is obtained by a GET request. Calculate salaries in EURO.
     */
    def payslip(exchangeRate: => Double, salaries: (Double, Double, Double)): (Double, Double, Double) = ???

    /**
     * EXERCISE 3
     * 
     * Using `System.nanoTime` measure execution time of a program then print it using `elapsedMs`
     * method.
     */
    def timed(thunk: Unit): Unit = ???

    // Helper method to print elapsed time in milliseconds.
    def elapsedMs(start: Long, end: Long): Unit = {
      val seconds: Int = ((end - start) / 1000000).toInt
      println(s"Elapsed time: $seconds ms")
    }
  }

  object TimedExample extends App {
    import exercises.timed

    // Sleep for random interval between 2 and 5 seconds
    def op() = {
      val time: Int = Random.nextInt(3) + 2
      println(s"Sleeping for $time seconds...")
      Thread.sleep(time * 1000)
      println(s"I'm awake!")
    }

    timed(op())
  }
}

package recursion {

  import scala.annotation.tailrec

  /**
   * A recursive function or method is one which calls itself in one of the branches.
   */
  object CountDownExample extends App {

    def countdown(n: Int): Unit =
      if (n == 0) println("BOOM!")
      else {
        println(n)
        Thread.sleep(1000)
        countdown(n - 1)
      }

    countdown(5)
  }

  /**
   * EXERCISE 1
   * 
   * Study following implementation. What will happen if we run it?
   */
  object FibonacciExample extends App {

    def fibonacci(n: Int): Long =
      if (n < 0) ???
      else if (n == 0) 0
      else if (n <= 2) 1
      else {
        val prev2 = fibonacci(n - 2)
        val prev1 = fibonacci(n - 1)
        prev2 + prev1
      }

    println((0 to 10).map(fibonacci).mkString(","))
  }

  /**
   * EXERCISE 2
   * 
   * Study following implementation. What will happen if we run it?
   */
  object FactorialExample extends App {

    def factorial(n: Int): Int =
      if (n == 1) 1
      else n * factorial(n - 1)


    println((1 to 5).map(factorial).mkString(","))
  }

  /**
   * Using a nested method we can keep the same external interface the same to the user, while
   * internally optimizing the algorithm.
   */
  object TailRecFactorialExample extends App {

    def tailRecFactorial(n: Int): Int = {

      /**
       * This method is tail-recursive, which means that for all branches of execution if the
       * recursive call is used it is last call.
       * 
       * This allows Scala to optimize reuse the same stack frame for the recursive call and avoid
       * overflowing the stack.
       * 
       * The @tailrec annotation will trigger compile-time error if the function is not
       * tail-recursive, however it is not necessary for the optimalization to kick in.
       */
      @tailrec
      def loop(acc: Int, n: Int): Int =
        if (n == 1) acc
        else loop(acc * n, n - 1)

      loop(1, n)
    }

    println((1 to 5).map(tailRecFactorial).mkString(","))
  }

  /**
   * EXERCISE 3
   * 
   * Implement tail-recursive fibonacci function.
   */
  object TailRecFibonacci extends App {

    def tailRecFibonacci(n: Int): Long = ???

    println((0 to 10).map(tailRecFibonacci).mkString(","))
  }

  /**
   * EXERCISE 4
   * 
   * Run the following benchmark. Note your findings.
   */
  object FibonacciBenchmark extends App {
    import evaluation.exercises.timed
    import FibonacciExample.fibonacci
    import TailRecFibonacci.tailRecFibonacci

    def runTest(label: String, sequence: => Seq[Long]): Unit = timed {
      println(s"[$label]")
      println(sequence.mkString(","))
    }

    runTest("fibonacci", (0 to 40).map(fibonacci))
    runTest("tailRecFibonacci", (0 to 40).map(tailRecFibonacci))
  }
}