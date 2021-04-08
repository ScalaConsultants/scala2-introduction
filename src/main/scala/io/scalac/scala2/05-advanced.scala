package io.scalac.scala2

package advanced {

  object syntax {

    // type aliases
    type User = (Int, String, String, String, Int)

    val user: User = (42, "foo@acme.com", "John", "Doe", 17)

    // basic collections
    val intArray    : Array[Int]       = Array(1, 2, 3, 4, 5)
    val intSequence : Seq[Int]         = Seq(1, 2, 3, 4, 5)
    val intList     : List[Int]        = List(1, 2, 3, 4, 5)
    val intVector   : Vector[Int]      = Vector(1, 2, 3, 4, 5)
    val intMap      : Map[String, Int] = Map("one" -> 1, "two" -> 2, "three" -> 3)

    // ranges
    val digits        : Range = (0 to 9)
    val reverseDigits : Range = (9 to 0 by -1)

    /**
     * Most collections have conversion methods into other structures defined. By convention these
     * methods are prefixed with `to`, eg. `toList`, `toArray`.
     */
    val listFromArray  : List[Int]   = intArray.toList
    val vectorFromList : Vector[Int] = intList.toVector

    /**
     * EXERCISE 1
     * 
     * Using Range and `toList` conversion create a list of first 100 integers.
     */
    val first100: List[Int] = ???
  
    /**
     * EXERCISE 2
     * 
     * Create a tuple of user data and ascribe the correct type.
     */
    val id        : Int     = 42
    val username  : String  = "coyote17"
    val email     : String  = "coyote@acme.com"
    val createdAt : Long    = 1614902400L
    val isActive  : Boolean = true
    val userRow = ???
  }

  /**
   * Scala provides traditional ways to iterate such as the while loop.
   */
  object WhileLoop extends App {
    val intArr: Array[Int]      = Array(1, 2, 3)
    val iterator: Iterator[Int] = intArr.iterator

    while (iterator.hasNext) {
      println(iterator.next())
    }

    // the preferred way, using collections API
    intArr.foreach(n => println(s"Got $n"))
  }

  object DoWhileLoop extends App {

    def repeatHello(times: Int): Unit = {
      var n: Int = 0

      do {
        println("Hello world")
        n = n + 1
      } while (n < times)
    }

    repeatHello(3)

    // the preferred way, using recursion
    def repeatWelcome(times: Int): Unit =
      if (times > 0){
        println("Welcome world")
        repeatWelcome(times - 1)
      }

    repeatWelcome(3)
  }

  /**
   * Scala standard library contains many useful collection data structures.
   * 
   * Collections can be divided into immutable (defaut) and mutable. Also some collections are lazy
   * meaning their elements will not be instantiated (and consume memory) until they are accessed.
   * 
   * The collections API is easy to use as the same set of methods is available on most structures.
   * 
   */
  object collections {

    val adjevtives = List(
      "angry", "bloody", "brave", "clever", "dangerous", "dark", "elegant", "friendly", "funny",
      "gentle", "grumpy", "happy", "helpful", "hungry", "important","impossible", "jealous",
      "kind", "lazy", "light", "lonely", "lucky", "modern", "mysterious", "nasty", "naughty",
      "plain", "powerful", "proud", "scary", "shiny", "sleepy", "tasty", "tired", "ugly", "weary",
      "wild", "worried", "zealous"
    )
    
    val nouns = List(
      "actor", "airport", "banana", "battery", "bed", "candle", "diamond", "dream", "egg", "eye",
      "fish", "flag", "flower", "forest", "garage", "garden", "ghost", "grass", "ice", "iron",
      "jelly", "knight", "lamp", "lion", "machine", "magician","needle", "night", "ocean",
      "orange", "pencil", "quill", "rainbow", "river", "rocket", "spoon", "stone", "train", "truck",
      "umbrella", "whale", "window", "yacht", "yak", "zebra", "zoo"
    )

    def makeTuples(length: Int, seed: Int = 42): List[(String, String)] = {
      val prng = new scala.util.Random(seed)
      def randomAdjective: String = adjevtives(prng.nextInt(adjevtives.length))
      def randomNoun: String      = nouns(prng.nextInt(nouns.length))

      List.fill(length)(randomAdjective -> randomNoun)
    }

    def makeInts(length: Int, seed: Int = 42): List[Int] = {
      val prng = new scala.util.Random(seed)

      List.fill(length)(prng.nextInt(100))
    }

    def show[A](label: String, list: List[A]): Unit = {
      println(s"\n:: $label List elements")
      list.foreach(println)
    }

    def showSorted(label: String, list: List[(String, String)]): Unit =
      show(label, list.sorted)

    def showTopN(label: String, map: Map[String, Int], n: Int): Unit =
      show(label, map.toList.sortBy(_._2)(Ordering.Int.reverse).take(n))
  }

  /**
   * EXERCISE 1
   * 
   * Use the `filter` method to remove all "plain" elements.
   */
  object FilterExample extends App {
    import collections._

    val list = makeTuples(10, seed = 42)
    showSorted("original", list)

    val filtered = ???
    showSorted("filtered", filtered)
  }

  /**
   * EXERCISE 2
   * 
   * Now use the `withFilter` method to do the same. Compare both methods and note your findings.
   */
  object WithFilterExample extends App {
    import collections._

    val list = makeTuples(10, seed = 42)
    showSorted("original", list)

    val filtered = ???
    showSorted("filtered", filtered)
  }

  /**
   * EXERCISE 3
   * 
   * Use the `map` method to concatenate the adjective and noun seperated by dash, transforming a
   * list of tuples into a list of strings.
   * 
   *   ("amused", "banana") => "amused-banana"
   */
  object MapExample extends App {
    import collections._

    val list = makeTuples(10, seed = 77)
    showSorted("original", list)

    val mapped = ???
    showSorted("mapped", mapped)
  }

  /**
   * EXERCISE 4
   * 
   * Use the `collect` method to filter and map elements in one go.
   * Remove "bloody" and "dangerous" elements, concatenate tuple otherwise.
   */
  object CollectExample extends App {
    import collections._

    val list = makeTuples(10, seed = 17)
    showSorted("original", list)

    val collected = ???
    showSorted("collected", collected)
  }

  /**
   * EXERCISE 5
   * 
   * Use the `sorted` method to sort the collection.
   */
  object SortedExample extends App {
    import collections._

    val list = makeInts(5, seed = 77)
    show("original", list)

    val sorted = ???
    show("sorted", sorted)
  }

  /**
   * EXERCISE 6
   * 
   * Use the `sortBy` method to sort the collection by the second element of tuple (noun).
   */
  object SortByExample extends App {
    import collections._

    val list = makeTuples(10, seed = 33)
    show("original", list)

    val sorted = ???
    show("sorted", sorted)
  }

  /**
   * EXERCISE 7
   * 
   * Use the `flatten` method to squash nested lists into a single list.
   */
  object FlattenExample extends App {
    import collections._

    val list1 = makeTuples(3, 10)
    val list2 = makeTuples(3, 20)
    val list3 = makeTuples(3, 30)
    val list = List(list1, list2, list3)
    show("original", list)

    val flat = ???
    show("flat", flat)
  }

  /**
   * EXERCISE 8
   * 
   * Use the `flatMap` and `map` methods transform nested lists and flatten the result in one go.
   */
  object FlatMapExample extends App {
    import collections._

    val list1 = makeTuples(3, 10)
    val list2 = makeTuples(3, 20)
    val list3 = makeTuples(3, 30)
    val list = List(list1, list2, list3)
    show("original", list)

    val flatMapped = list
    show("flatMapped", flatMapped)
  }

  /**
   * EXERCISE 9
   * 
   * Use the `foldLeft` method to reduce the list into a Map[String, Int], where the key is the
   * adjective and the value is how many occurences were found.
   */
  object FoldLeftExample extends App {
    import collections._

    val list = makeTuples(100)
    val init = Map.empty[String, Int]

    val folded: Map[String, Int] = ???
    showTopN("folded", folded, 10)
  }

  /**
   * EXERCISE 10
   * 
   * Use the `groupBy` method and `view.mapValues` to achieve the same result as in previous
   * exercise.
   */
  object GroupByExample extends App {
    import collections._

    val list = makeTuples(100)
    val init = Map.empty[String, Int]
    
    val grouped: Map[String, Int] = ???
    showTopN("grouped", grouped, 10)
  }

  /**
   * EXERCISE 11
   * 
   * Use the `min` and `max` methods to find the least and largest elements.
   */
  object MinMaxExample extends App {
    import collections._

    val list = makeInts(10)
    show("elements", list.sorted)

    val min: Int = ???
    val max: Int = ???
    println(s"\nmin: $min\nmax: $max")
  }

  /**
   * EXERCISE 12
   * 
   * Use the `minBy` and `maxBy` method to find shortest and longest pair.
   */
  object MinByMaxByExample extends App {
    import collections._

    val list = makeTuples(10)
    val pairLength
      : ((String, String)) => Int
      = t => t._1.length + t._2.length

    show("elements", list.sortBy(pairLength))

    val min: (String , String) = ???
    val max: (String , String) = ???
    println(s"\nmin: $min\nmax: $max")
  }

  object iteration1 {
    
    /**
     * A for expression is syntatic sugar for a series of `map`, `foreach` and `withFilter` methods.
     * Since these methods are defined for all iterables, you can use every collection data type as
     * a generator.
     */
    def printOddUpTo(n: Int): Unit = {
      val generator: Range = 1 to n

      for (n <- generator if n % 2 == 1; msg = s"Next odd: $n")
        println(msg)
    }

    /**
     * The following is the desugared equivalent:
     */
    def printOddUpToDesugared(n: Int): Unit = {
      val generator: Range = 1 to n

      generator
        .withFilter(n => n % 2 == 1)
        .map { n =>
          val msg = s"Next odd: $n"
          (n, msg)
        }
        .foreach { case (n, msg) => println(msg) }
    }
  }

  /**
   * EXERCISE 1
   * 
   * Run the example. Observe the side effects and the return value.
   */
  object ForLoopExample extends App {
    import iteration1.printOddUpTo

    val result = printOddUpTo(8)
    println(s"Return value: $result")
  }

  object iteration2 {

    /**
     * A for expression can use multiple generators. In such case the loop will be repeated for
     * all combinations of values extracted from generators in depth-search-first order.
     */
    def repeatAll(greetings: List[String], names: List[String]): Unit =
      for (greeting <- greetings; name <- names) {
        val message = s"$greeting $name!"
        println(message)
      }

    /**
     * The following is the desugared equivalent:
     */
    def repeatAllDesugared(greetings: List[String], names: List[String]): Unit =
      greetings
        .foreach { greeting => 
          names.foreach { name =>
            val message = s"$greeting $name!"
            println(message)
          }
        }
  }

  /**
   * EXERCISE 2
   * 
   * Run the example. Observe the order in which combinations were generated.
   */
  object ForLoopManyExample extends App {
    import iteration2.repeatAll

    val greetings = List("Hello", "Hi", "Welcome")
    val names     = List("Fred", "Barney")
    repeatAll(greetings, names)
  }

  object iteration3 {

    /**
     * A sequence comprehension is a for expression yields values instead of callsing side effects.
     * The result of the whole expression will be a sequence.
     */
    def generate2dPlane(width: Int, height: Int): Seq[(Int, Int)] = {
      val xs = 0 until width
      val ys = 0 until height

      for (x <- xs; y <- ys) yield (x, y)
    }

    /**
     * The following is the desugared equivalent:
     */
    def generate2dPlaneDesugared(width: Int, height: Int): Seq[(Int, Int)] = {
      val xs = 0 until width
      val ys = 0 until height

      xs.flatMap { x =>
        ys.map(y => (x, y))
      }
    }
  }

  object ForYieldExample extends App {
    import iteration3.generate2dPlane

    val plane = generate2dPlane(2, 3)
    println(plane)
  }

  object iteration4 {

    /**
     * A for comprehension is syntatic sugar for expressing sequence comprehensions. Value
     * assignments and filters are also supported.
     */
    def generateNames(firstNames: Seq[String], lastNames: Seq[String]): Seq[String] =
      for {
        first    <- firstNames
        last     <- lastNames
        fullName = s"$first $last"
        if fullName.length < 16
      } yield fullName

    /**
     * The following is the desugared equivalent:
     */
    def generateNamesDesugared(firstNames: Seq[String], lastNames: Seq[String]): Seq[String] =
      firstNames.flatMap { first =>
        lastNames.map { last =>
          val fullName = s"$first $last"
          (last, fullName)
        }
        .withFilter(_._2.length < 16)
        .map(_._2)
      }
  }

  /**
   * EXERCISE 3
   * 
   * Run the example. Observe the generated sequence and its return type.
   */
  object ForComprehensionExample extends App {
    import iteration4.generateNames

    val first = List("Kai", "Jeremy", "Constantine")
    val last  = Vector("Jefferson", "Smith", "Ali")
    val names = generateNames(first, last)
    println(names)
  }
}

/**
 * Immutability means that a value cannot be changed. In Java to make a field immutable you have to
 * explicitly mark it as `final`. In Scala fields are immutable by default and you have to
 * explicitly mark them as `var` to make them mutable.
 */
package immutability {

  class ScalaBox(value: String) {
    def show(): Unit = println(s"ScalaBox :: value = $value")
  }

  /**
   * EXERCISE 1
   * 
   * Try to assign "Jim" to each box `value` field. Note your findings.
   */
  object ImmutabilityExample extends App { 

    val javaBox = new JavaBox("Jack")
    javaBox.show()

    val scalaBox = new ScalaBox("Jack")
    scalaBox.show()
  }

  /**
   * Immutable values and data structures are easier to reason about. By definition you cannot
   * change an immutable value, so if you want to return updated state you have to produce a 
   * new value.
   * 
   * This enables local reasoning - you can simply focus on your local scope and ignore where the 
   * value came from and where else is it used - it does not matter, you are working with a local
   * copy anyways.
   * 
   * Associative functions working with immutable data can be easily and safely parallelized.
   */
  object ParallelSum extends App {

    import scala.collection.parallel.CollectionConverters._

    val values = (1 to 1000).par

    def mutableSum: Int = {
      var sum: Int = 0
      values.foreach(n => sum += n)
      sum
    }

    def immutableSum: Int =
      values.fold(0)(_ + _)

    val mutableResults = (1 to 5).map(_ => mutableSum).mkString("[", ", ", "]")
    println(s"Mutable   : $mutableResults")

    val immutableResults = (1 to 5).map(_ => immutableSum).mkString("[", ", ", "]")
    println(s"Immutable : $immutableResults")
  }
}

/**
 * Generics allow you delay the choice of concrete type. The concrete type is decided at call-site,
 * either by excplicitly provided type parameter or inferred by the compiler based on arguments.
 * 
 * Generic programming is "forgetting" unnecessary details and "remembering" only what is relevant.
 */
package polymorphism {

  /**
   * Polymorphic methods are methods which take type parameters.
   */
  object SemigroupExample extends App {

    def fold[A](zero: A)(combine: (A, A) => A)(list: List[A]): A = {
      def loop(acc: A, list: List[A]): A =
        list match {
          case Nil          => acc
          case head :: tail => loop(combine(acc, head), tail)
        }

      loop(zero, list)
    }

    val sumAll     = fold(0)(_ + _)(_)
    val productAll = fold(1)(_ * _)(_)

    val values = (1 to 5).toList

    println(s"+ all: $values = ${sumAll(values)}")
    println(s"* all: $values = ${productAll(values)}")
  }

  /**
   * You can restrict generic parameter to the subtypes of some type using "Upper Bound" syntax.
   * This means you can only call this method with a value of type `AnyVal` or its subtypes.
   */
  object UpperBoundExample extends App {

    /**
     * EXERCISE 1
     * 
     * Try pattern match for `String` type. Note your findings.
     */
    def prettyPrint[A <: AnyVal](value: A): String =
      value match {
        case v: Boolean => s"1-bit logical value: $v"
        case v: Int     => s"32-bit signed numerical valeu: $v"
        case v: Long    => s"64-bit signed numerical value: $v"
        case null       => "null"
        case _          => ???
      }
  }

  /**
   * You can have multiple type parameters.
   */
  object MultipleTypeParams extends App {
    
    def collect[A, B](predicate: A => Boolean, transformation: A => B)(list: List[A]): List[B] =
      list.filter(predicate).map(transformation)
    
    val getOddLongs   = collect[Int, Long](_ % 2 == 1, _.toLong)(_)
    val getEvenLongs  = collect[Int, Long](_ % 2 == 0, _.toLong)(_)

    val values = (1 to 10).toList

    def show[A](list: List[A]): String = list.mkString("[", ", ", "]")

    println(
      s"""N      = ${show(values)}
         |N_odd  = ${show(getOddLongs(values))}
         |N_even = ${show(getEvenLongs(values))}""".stripMargin
    )
  }

  /**
   * You can also restrict generic parameter to the supertypes of some type using "Lower Bound"
   * syntax. This allows us to widen the result type.
   */
  object RelatedTypeParams extends App {

    abstract class Animal
    abstract class Cat extends Animal
    abstract class Dog extends Animal

    final class Bengal  extends Cat { override def toString: String = "Bengal"}
    final class Persian extends Cat { override def toString: String = "Persian"}

    final class Malamute extends Dog { override def toString: String = "Malamute"}
    final class Labrador extends Dog { override def toString: String = "Labrador"}

    def prepend[A, B >: A](list: List[A], elem: B): List[B] = 
      list.prepended(elem)

    val cats: List[Cat] = List(new Bengal, new Persian)
    val dogs: List[Dog] = List(new Malamute, new Labrador)

    /**
     * Given a list of `Cat`, we can prepend any element `B` to it,
     * as long as `B` is a supertype of `Cat`.
     * 
     * Given a value, the compiler will try to infer type `B` to the most specific type that
     * matches. In this case the compiler tries to match `Malamute`, then `Dog`, both of which are
     * not supertypes of `Cat`. Next the compiler tries `Animal` and finds a match, so the inferred
     * type for `B` will be `Animal`.
     * 
     * The final inferred type will be `prepend[Cat, Animal]`, so the function will return type will
     * be `List[Animal]`.
     * 
     * This pattern is called type widening. We've widened a specific `List[Cat]` to a broader type
     * `List[Animal]`.
     */
    val animals1 = prepend(cats, new Malamute)
    val animals2 = prepend(dogs, new Persian)

    println(animals1)
    println(animals2)
  }    
}