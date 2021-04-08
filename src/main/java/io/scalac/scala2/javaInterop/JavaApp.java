package io.scalac.scala2.javaInterop;

public class JavaApp {

    /**
     * Scala types can be contructed in Java.
     */
    public static void main(String[] args) {
        System.out.println("-- JavaApp --");

        Pet cat = new Cat("Lil", 7);
        System.out.println(cat.show());

        Pet dog = new Dog("Rex", 2);
        System.out.println(dog.show());

        Pet bird = new Bird("Tweety", 1);
        System.out.println(bird.show());
    }
}