package io.scalac.scala2

/**
 * Implicits are a low level mechanism driven by types for the compiler to fill in the gaps. In
 * Scala 2 there are multiple patterns based on this mechanism and they are often confused.
 * 
 * The first step towards understanding implicits is learning to distinguish the patterns and their
 * purpose.
 */
package implicits {

  /**
   * Scala is a strongly typed language, which means any function can only be called with arguments
   * that match the type signature.
   * 
   * In order to work, implicit conversions have to be enabled either by:
   *   - by importing `import scala.language.implicitConversions` in given scope
   *   - or globally for the whole project with `-language:implicitConversions` compiler flag
   * 
   * In this project we've already enabled it globally (see `build.sbt` file).
   * 
   * It is highly recommended to only use conversions for specific types. For example, an
   * automatic conversion from `Int` to `String` types could lead to weird bugs.
   */
  package conversions {

    case class Employee(fullName: String)
    case class Person(firstName: String, lastName: String)
    case class Pet(name: String)

    trait Example {
      val pet: Pet = Pet("Rex")
      val person: Person = Person("John", "Doe")
      def show(employee: Employee): Unit = println(employee.fullName)
    }

    /**
     * EXERCISE 1
     * 
     * Try to pass `person` to `show` method. Note your findings.
     */
    object MatchFailure extends Example {
      show(???)
    }

    object ConversionSyntax extends App with Example {

      /**
       * Defining an implicit conversion is like giving the compiler a recipe to translate from one
       * type to another. An implicit conversion is just a single argument method prefixed with
       * `implicit` modifier.
       * 
       * The conversion is automatically available in current scope.
       */
      implicit def personToEmployee(person: Person): Employee =
        Employee(s"${person.firstName} ${person.lastName}")

      show(person)
    }

    /**
     * EXERCISE 2
     * 
     * Import `personToEmployee` conversion from previous example to make this example compile. 
     */
    object ImportConversion extends App with Example {
      // show(person)
    }

    /**
     * When dealing with not matching types, before giving up the compiler searches for implicit
     * conversions. The first place the compiler is searching is the current scope, however if that
     * fails the compiler will also search the Companion objects of both types it tries to match.
     */
    object CompanionObjectSearch extends App with Example {

      // The compiler fails to match `Pet` to `Employee` type.
      // show(pet)
    }

    /**
     * EXERCISE 3
     * 
     * Define an implicit conversion from `Pet` to `Employee` type to make the previous example
     * compile.
     */
    object Pet
  }

  /**
   * Implicit parameters were designed to allow automatic threading of some contextual information.
   * Regular and implicit parameters cannot be mixed. An implicit parameter list is a parameter
   * list that starts with `implicit` keyword and must be the last parameter list.
   * 
   * Implicit values are regular values marked with `implicit` modifier. These values can be used
   * as an implicit parameter to a function.
   * 
   * If more than one value matches the implicit search, the compiler will give up and we will get
   * a compile time ambigous implicits error.
   */
  package parameters {

    case class LogContext(level: LogLevel)

    sealed abstract class LogLevel(private val level: Int, label: String) extends Ordered[LogLevel] {
      self =>
        def compare(that: LogLevel) = self.level compare that.level

        override def toString(): String = label
    }

    object LogLevel {
      case object Error extends LogLevel(1, "error")
      case object Info  extends LogLevel(2, "info")
      case object Debug extends LogLevel(3, "debug")
    }

    class Logger {

      /**
       * Requires `LogContext` parameter that may be provided implicitly by the compiler.
       */
      def log(message: => String, level: LogLevel)(implicit ctx: LogContext): Unit =
        if (level <= ctx.level) println(s"[$level] $message")

      def debug(message: => String)(implicit ctx: LogContext): Unit =
        log(message, LogLevel.Debug)

      /**
       * EXERCISE 1
       * 
       * Thie method requires  `LogContext` parameter that may be provided implicitly by the
       * compiler, however the value is later passed explicitly to downstream `log` method.
       * 
       * Refactor this method to pass `LogContext` explicitly.
       */
      def info(message: => String)(implicit ctx: LogContext): Unit =
        log(message, LogLevel.Info)(ctx)

      /**
       * EXERCISE 2
       * 
       * This method requires `LogContext` to be passed in explicitly. Refactor it to thread the
       * `LogContext` value impicitly.
       */
      def error(message: => String)(ctx: LogContext): Unit = {
        implicit val c: LogContext = ctx

        log(message, LogLevel.Error)
      }
    }

    object ExplicitUsageExample extends App {
      val logger = new Logger
      val logCtx = LogContext(LogLevel.Debug)

      logger.debug("Explicitly providing LogContext")(logCtx)
      logger.info("Explicitly providing LogContext")(logCtx)
    }

    object ImplicitUsageExample extends App {
      val logger = new Logger
      implicit val logCtx = LogContext(LogLevel.Debug)

      logger.debug("Implicitly providing LogContext")
      logger.info("Implicitly providing LogContext")
    }

    class UserRepo(logger: Logger) {
      def findUser()(implicit ctx: LogContext): Unit = logger.debug("Call to UserRepo.findUser")
    }

    class SmsService(logger: Logger) {
      def sendSms()(implicit ctx: LogContext): Unit = logger.debug("Call to SmsService.sendSms")
    }

    class EmailService(logger: Logger) {
      def sendEmail()(implicit ctx: LogContext): Unit = logger.debug("Call to EmailService.sendEmail")
    }

    class UserService(logger: Logger, repo: UserRepo, sms: SmsService, email: EmailService) {
      def pushNotication()(implicit ctx: LogContext): Unit =  {
        logger.debug("Call to UserService.pushNotication")
        repo.findUser()
        sms.sendSms()
        email.sendEmail()
      }
    }

    class UserApi(logger: Logger, users: UserService) {
      def handleRequest()(implicit ctx: LogContext): Unit = {
        logger.debug("Call to UserApi.handleRequest")
        users.pushNotication()
      }
    }

    /**
     * The following example shows how implicits can solve the pain of threading contextual data
     * such as configs via multiple levels of the application.
     */
    object DeeplyThreadingExample extends App {

      val logger = new Logger
      implicit val logCtx = LogContext(LogLevel.Debug)

      // typical constructor based dependency injection
      val repo  = new UserRepo(logger)
      val sms   = new SmsService(logger)
      val email = new EmailService(logger)
      val users = new UserService(logger, repo, sms, email)
      val api   = new UserApi(logger, users)

      api.handleRequest()
    }
  }

  /**
   * Sometimes we have to use some external library or code that we have no control over, but we
   * would like to be able to extend the available API.
   * 
   * Extension methods, also called "Pimp my library" pattern, is a pattern that uses implicit
   * conversions as the underlaying mechanism for solving that problem.
   * 
   * When a compiler sees an invocation on an object of a given type, it looks up the structure of
   * that type for a matching member (value or method). If the type does not have a matching member
   * the compiler will search all of supertypes and traits. If it still cannot find a match, before
   * giving up the compiler will lookup all implicit conversions from that type to other types.
   * 
   * If it finds a unique conversion to a type that has a matching member, it will automatically
   * perform the conversion for us and call that member.
   */
  package extensionMethods {

    import javax.measure.Quantity
    import javax.measure.quantity.Length
    import tech.units.indriya.quantity.Quantities
    import tech.units.indriya.unit.Units.METRE

    object ImplicitConversions {

      /**
       * The first component of the pattern is the wrapper object, that contains extension methods.
       * By convention these classes are named after the underlying type suffixed with `Ops`.
       */
      class DoubleOps(value: Double) {
        def mm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(1000))
        def cm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(100))
        def m: Quantity[Length]  = Quantities.getQuantity(value, METRE)
        def km: Quantity[Length] = Quantities.getQuantity(value, METRE.multiply(1000))
      }

      /**
       * The second component of the pattern is the implicit conversion from the type that we want
       * to extend (most likely from 3rd party library) to our wrapper object.
       */
      implicit def toDoubleOps(value: Double): DoubleOps = new DoubleOps(value)

      class LengthOps(self: Quantity[Length]) {
        def +(that: Quantity[Length]): Quantity[Length] = self.add(that)
        def -(that: Quantity[Length]): Quantity[Length] = self.subtract(that)
      }

      implicit def toLengthOps(value: Quantity[Length]): LengthOps = new LengthOps(value)
    }

    object ImplicitConversionsExample extends App {
      import ImplicitConversions._

      val distance = 1.km - 500.m + 20.cm
      println(s"[implicit conversions] It is $distance")
    }

    /**
     * The "Pimp my library" pattern was so popular that later it received special syntax called
     * "Implicit class". By adding the `implicit` keyword modifier to the wrapper class, the
     * implicit conversion will be autogenerated for us by the compiler.
     */
    object ImplicitClass {

      implicit class DoubleOps(value: Double) {
        def mm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(1000))
        def cm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(100))
        def m: Quantity[Length]  = Quantities.getQuantity(value, METRE)
        def km: Quantity[Length] = Quantities.getQuantity(value, METRE.multiply(1000))
      }

      implicit class LengthOps(self: Quantity[Length]) {
        def +(that: Quantity[Length]): Quantity[Length] = self.add(that)
        def -(that: Quantity[Length]): Quantity[Length] = self.subtract(that)
      }
    }

    object ImplicitClassExample extends App {
      import ImplicitClass._

      val distance = 1.km - 500.m + 20.cm
      println(s"[implicit class] It is $distance")
    }
  }

  /**
   * Another very common pattern (especially for library authors) is called "Implicit evidence".
   * 
   * In short, by requiring an implicit parameter of some type it bans the user from invoking that
   * method unless an implicit instance of that type is available in scope.
   */
  package implicitEvidence {

    import scala.annotation.implicitAmbiguous

    // adapted from github.com/zio/zio
    sealed abstract class CanFail[-E]
    object CanFail extends CanFail[Any] {

      /**
       * When searching for `CanFail` instance for `Nothing` type, the compiler will find two
       * implicit values that match. Therefore the compiler will fail with ambigous implicits
       * error.
       * 
       * The following annotation allows us to customize the error message.
       */ 
      @implicitAmbiguous("This error handling operation assumes your Result can fail.")
      implicit val canFailAmbiguous1: CanFail[Nothing] = CanFail
      implicit val canFailAmbiguous2: CanFail[Nothing] = CanFail

      /**
       * When searching for `CanFail` instance for any other type, the compiler will find this
       * generic definition, efectfully proving the type was not `Nothing`.
       */
      implicit def canFail[E]: CanFail[E] = CanFail
    }

    sealed trait Result[+E, +A] { self =>

      def orElse[E2, A1 >: A](that: => Result[E2, A1])(implicit ev: CanFail[E]): Result[E2, A1] =
        self match {
          case Error(_)             => that
          case success @ Success(_) => success
        }
    }

    case class Success[A](value: A) extends Result[Nothing, A]
    case class Error[E](error: E) extends Result[E, Nothing]

    object ImplicitEvidenceExample extends App {
      val fallback = Success("fallback")

      val v1 = Success("successful")
      val v2 = Error("error")

      /**
       * EXERCISE 1
       * 
       * Try adding fallback value using `orElse` combinator to both values. Note your findings
       */
      val v1OrElse = v1
      val v2OrElse = v2

      println(v1OrElse)
      println(v2OrElse)
    }
  }

  /**
   * Scala's strong type system and powerful features like implicit conversions enable programmers
   * to construct their own mini-languages embedded in Scala to accurately describe specific
   * domains and constrain the user of the API to only valid choices.
   */
  package dsl {

    sealed abstract class Html(val label: String, val contents: Html*) {
      def render(level: Int)(implicit s: StringBuilder): Unit = {
        val ident = "\n" + ("  " * level)
        s.append(s"$ident<$label>")
        contents.foreach(_.render(level + 1))
        s.append(s"$ident</$label>")
        ()
      }
    }

    object Html {

      case class p(override val contents: Html*)   extends Html("p")
      case class b(override val contents: Html*)   extends Html("b")
      case class h1(override val contents: Html*)  extends Html("h1")
      case class div(override val contents: Html*) extends Html("div")
      case class text(value: String)               extends Html("text") {
        override def render(level: Int)(implicit s: StringBuilder): Unit = {
          s.append(value)
          ()
        }
      }
      
      def html(contents: Html*): String = {
        implicit val s: StringBuilder = new StringBuilder
        s.append("<html>")
        contents.foreach(_.render(1))
        s.append("\n</html>")
        s.toString
      }
      
      implicit def toText(value: String): text = text(value)
    }

    object HtmlDslExample extends App {
      import Html._

      val page = html(
        div(
          h1("Scala 2 DSL"),
          p(
            "Lorem ipsum dolor sit amet, ",
            b("consectetur"),
            " adipiscing elit."
          )
        ),
        div("footer")
      )

      println(page)
    }
  }
}