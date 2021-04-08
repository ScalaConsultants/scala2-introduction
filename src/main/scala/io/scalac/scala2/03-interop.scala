package io.scalac.scala2

/**
 * Scala has great interoperability with Java:
 *
 *   - Java types can be extended and instantiated in Scala codebase
 *   - you can easily add Java dependencies to your Scala build
 *
 *   - Scala types can be extended and instantiated in Java codebase
 *   - you can easily add Scala dependencies to your Java build
 */
package javaInterop {

  /**
   * Animal is an interface defined in Java
   *
   * Pet is a trait defined in Scala.
   */
  trait Pet extends Animal {
    def name: String
    def age: Int

    def speak(): String

    def show(): String = s"$age year old $kind named $name sais: `${speak()}`"
  }

  /**
   * Cat is a class defined in Java that extends Pet
   *
   * Dog is a class defined in Scala that extends Pet
   */
  class Dog(val name: String, val age: Int) extends Pet {
    val kind: String = "Dog"

    def speak(): String = "Woof! Woof!"
  }

  /**
   * Arguments with default values are optional in Scala, but not in Java.
   */
  class Bird(val name: String = "Tweety", val age: Int = 1) extends Pet {
    val kind: String = "Bird"

    def speak(): String = "Tweet! Tweet!"
  }

  /**
   * JavaApp is an application defined in Java that creates and uses classes and interfaces defined
   * in both Java and Scala.
   *
   * ScalaApp is an application defined in Scala that creates and uses classes and interfaces
   * defined in both Java and Scala.
   */
  object ScalaApp extends App {
    println("-- ScalaApp --")

    val cat = new Cat("Lil", 7)
    println(cat.show())

    val dog = new Dog("Rex", 2)
    println(dog.show())

    val bird = new Bird()
    println(bird.show())
  }

  /**
   * For convinience, we'll start both apps from here.
   */
  object RunBothApps extends App {
    JavaApp.main(args)
    ScalaApp.main(args)
  }
}
