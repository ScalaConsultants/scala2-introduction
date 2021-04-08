package io.scalac.scala2

/**
 * SBT is the goto build tool for most Scala developers.
 *
 * The build is itself a Scala project with expressive and type-safe DSL.
 *
 * Thanks to parallel processing, change watching and incremental compilation developers benefit
 * from quick compiler feedback and can iterate faster.
 *
 * With over 100 community maintained plugins SBT can handle any workflow you throw at it:
 *   - static analysis (scalafix, wartremover, sonarqube, scoverage)
 *   - running tests and benchmarks (as well as pushing reports)
 *   - cross compiling to various platforms (JS, JVM, Native)
 *   - packaging (JARs, Docker images)
 *   - generating code
 *   - generating documentation
 *   - compiling/compressing assets (gzip, uglifyjs, less)
 * ... and more!
 */
package object scalaBuiltTool {

  /**
   * See build file `build.sbt`
   */
  val sbtDocs: String = "https://www.scala-sbt.org/1.x/docs/"

  /**
   * See plugins file `project/plugin.sbt`
   */
  val scalafmtDocs: String = "https://scalameta.org/scalafmt/docs/installation.html"
}

/**
 * Formatting configuration is in the `.scalafmt.conf` file.
 *
 * Refer to https://scalameta.org/scalafmt/docs/configuration.html for documentation.
 */
package formatting {

  /**
   * EXERCISE 1
   *
   * Remove the `format: off` comment to apply auto-formatting.
   */
  object wrongIndentation {
    // format: off
          object MisalignedA
    object MisalignedB
              object MisalignedC
                                        object MisalignedD
      object MisalignedE 
  }

  /**
   * EXERCISE 2
   *
   * Remove the `format: off` comment to apply auto-formatting.
   */
  object tooLongLines {
    // format: off
    class TitanicPassenger(passengerId: String, survived: Boolean, passengerClass: Int, name: String, sex: String, age: Int, numSiblingsOrSpouseOnBoard: Int, numParentsOrChindrenOnBoard: Int)
  }

  /**
   * EXERCISE 3
   *
   * Remove the `format: off` comment to apply auto-formatting.
   */
  object flagAntipattern {
    // format: off
    class Cache(param1: String,
                param2: String,
                param3: String,
                param4: String,
                param5: String,
                param6: String,
                param7: String,
                param8: String) 

    class UserAccountsRepositoryLive(param1: String,
                                    param2: String,
                                    param3: String,
                                    param4: String,
                                    param5: String,
                                    param6: String,
                                    param7: String,
                                    param8: String) 

    class ConsoleLogger(param1: String,
                        param2: String, 
                        param3: String,
                        param4: String,
                        param5: String,
                        param6: String,
                        param7: String,
                        param8: String)
  }

  /**
   * EXERCISE 4
   *
   * Remove the `format: off` comment to apply auto-formatting.
   */
  object patternMatchAlign {
    // format: off
    def marvelSuperheroes(name: String): String =
      name match {
          case "Peter Parker" => "Spidey"
        case "Logan"                => "Wolverine"
                      case "Ororo Munroe" => "Storm" 
            case _ => "No idea"
      }
  }

  /**
   * EXERCISE 5
   *
   * Remove the `format: off` comment to apply auto-formatting.
   */
  object inconsistentCommentStyle {
    // format: off

    /**
      * Lorem ipsum dolor sit amet.
      */
    def scalaDoc(): Unit = ???

    /**
     * Lorem ipsum dolor sit amet.
     */
    def javaDoc(): Unit = ???
  }
}
