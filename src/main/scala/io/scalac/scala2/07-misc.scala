package io.scalac.scala2

/**
 * One of the useful patterns in Scala is to represent domain errors with values. Using sealed
 * traits for exhaustivity checks and `-Xfatal-warnings` flag to turn warnings into compile
 * time errors, we can force the caller to handle all possible cases.
 */
package errors {

  sealed trait PasswordError
  object PasswordError {
    case object Empty                   extends PasswordError
    case class TooShort(bound: Int)     extends PasswordError
    case class TooLong(bound: Int)      extends PasswordError
    case class NotEnoughLower(min: Int) extends PasswordError
    case class NotEnoughUpper(min: Int) extends PasswordError
  }

  object PasswordValidator {
    import PasswordError._

    type Validator = String => Either[PasswordError, String]

    private val notEmpty : Validator = p => Either.cond(p.nonEmpty, p, Empty)
    private val min10char: Validator = p => Either.cond(p.length > 10, p, TooShort(10))
    private val max32char: Validator = p => Either.cond(p.length < 32, p, TooLong(32))
    private val hasLower : Validator = p => Either.cond(p.toUpperCase != p, p, NotEnoughLower(1))
    private val hasUpper : Validator = p => Either.cond(p.toLowerCase != p, p, NotEnoughUpper(1))

    def apply(password: String): Either[PasswordError, String] =
      notEmpty(password)
        .flatMap(min10char)
        .flatMap(max32char)
        .flatMap(hasLower)
        .flatMap(hasUpper)
  }

  /**
   * By turning your errors into data you gain the ability to delegate handler details elsewhere.
   */
  object PasswordExample extends App {
    import PasswordError._

    /**
     * EXERCISE 1
     * 
     * Comment out one of the cases. Note your findings.
     */
    def message(error: PasswordError): String =
      error match {
        case Empty               => "Password cannot be empty"
        case TooShort(bound)     => s"Password must be at least $bound characters long"
        case TooLong(bound)      => s"Password may not exceed $bound characters"
        case NotEnoughLower(min) => s"Password must contain at least $min lowercase characters"
        case NotEnoughUpper(min) => s"Password must contain at least $min uppercase characters"
      }

    val result = PasswordValidator("manhattan47")

    result match {
      case Left(error) => println(message(error))
      case Right(_)    => println("Password is valid")
    }
  }
}

/**
 * There are cases where a function is not able to return a value. A typical case being a database
 * loopup by unique id. There can only exsist at most one record (otherwise the id is not unique),
 * so the query can return either exacly one, or no values.
 * 
 * On JVM you can always return a null reference, however that pattern is problematic, as the
 * information about value optionality is not propagated or checked anywhere, and if you try to
 * access any member on a null reference JVM will throw the infamous `NullPointerException` at
 * runtime. This lead to defensive programming, checking for nulls everywhere.
 * 
 * In Scala many developers choose to pretend `null` does not exist, instead we encode the
 * optionality using the `Option` monad. 
 */
package emptyValues {

  object OptionExample extends App {
    def externalCode: AnyRef = null

    Option(externalCode) match {
      case Some(_) => println("Handling some value...")
      case None    => println("Handling missing value...")
    }
  }

  object OptionComposability {

    def findUser: Option[String] = ???
    def findAccount: Option[String] = ???
    def findProfile: Option[String] = ???

    /**
     * Option is a monad, so it can be composed sequentially.
     * Hover over `userData` to see the return type.
     */
    val userData =
      for {
        user    <- findUser
        account <- findAccount
        profile <- findProfile.orElse(Some("default profile"))
      } yield (user, account, profile)
  }
}

/**
 * When modeling the business domain often the primitive types turn out to be too wide. For example
 * the simplest way to store email address is the `String` type, however not all strings are valid
 * emails.
 * 
 * To distinguish between any `String` we create a so called "New Type", which is basically a case
 * class wrapper around the `String`. Using the `private` visibility modifier, we hide the primary
 * constructor, which now can only be called from withing the class itself or it's companion
 * object.
 * 
 * Next, in the companion object we create a new constructor, which performs validation of our
 * domain constraints and returns an `Option` of our domain object, falling back to `None` if the
 * validation fails.
 * 
 * This pattern is called "Smart constructor".
 */
package smartConstructors {

  /**
   * This definition captures the idea behind "New types" and "Smart constructors", however due to
   * a bug in Scala it fails to completely block creation of invalid values.
   */
  object SimpleDefinition {
    case class Username private (value: String)

    object Username {

      def fromString(v: String): Option[Username] =
        if (v.matches("^@[a-z0-9]{4,32}$")) Some(new Username(v)) else None

      def upper(username: Username): Username =
        username.copy(value = username.value.toUpperCase)

      val johndoe: Username = new Username("@johndoe")
    }
  }

  object SimpleDefinitionExample extends App {
    import SimpleDefinition._

    val userA = Username.fromString("foo")
    val userB = Username.fromString("@foobar")

    println(s"userA = $userA")
    println(s"userB = $userB")

    // primary constructor not available - as expected
    //val userC = new Username("foo")

    // unfortunately autogenerated `apply` and `copy`
    // can be used to create invalid values :(
    val userD = Username.apply("foo")
    val userE = Username.johndoe.copy(value = "foo")

    println(s"userD = $userD")
    println(s"userE = $userE")
  }

  /**
   * This definition is a workaround the issue of autogenerated methods. In Scala 3 this workaround
   * is no longer necessary.
   */
  object FixedDefinition {
    sealed abstract case class Username private (value: String)

    object Username {

      def fromString(v: String): Option[Username] =
        if (v.matches("^@[a-z0-9]{4,32}$")) Some(new Username(v) {}) else None

      def upper(username: Username): Username =
        new Username(username.value.toUpperCase) {}
      //   username.copy(value = username.value.toUpperCase)
    }
  }

  object FixedDefinitionExample extends App {
    import FixedDefinition._
    
    val userA = Username.fromString("foo")
    val userB = Username.fromString("@foobar")

    println(s"userA = $userA")
    println(s"userB = $userB")

    // blocked by abstract modified - no longer generated
    // val userC = Username.apply("foo")
    // val userD = userB.get.copy(value = "foo")
  }

  /**
   * Example event sourcing domain.
   */
  object domainModelingExample {

    import java.util.UUID
    import scala.util.Try

    sealed abstract case class Email private (value: String)
    object Email {
      def fromString(v: String): Option[Email] =
        if (isValidEmail(v)) Some(new Email(v) {}) else None

      def isValidEmail(v: String): Boolean =
        v.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")
    }

    sealed abstract case class UserId private (value: UUID)
    object UserId {
      def fromString(v: String): Option[UserId] =
        Try(UUID.fromString(v)).toOption.map(new UserId(_) {})

      def makeRandom: UserId = new UserId(UUID.randomUUID) {}
    }

    sealed trait UserCommand
    final case class SignUp(email: Email)                       extends UserCommand
    final case class AddFriend(id: UserId, friendId: UserId)    extends UserCommand
    final case class RemoveFriend(id: UserId, friendId: UserId) extends UserCommand

    sealed trait UserEvent { def userId: UserId }
    final case class UserCreated(userId: UserId, email: Email)       extends UserEvent
    final case class FriendAdded(userId: UserId, friendId: UserId)   extends UserEvent
    final case class FriendRemoved(userId: UserId, friendId: UserId) extends UserEvent
  }
}