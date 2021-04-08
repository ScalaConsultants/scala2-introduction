package io.scalac.scala2.javaInterop;

/**
 * Scala interfaces can be implemented in Java.
 */
public class Cat implements Pet {

    private String _name;
    private int _age;

    public Cat(String name, int age) {
        _name = name;
        _age = age;
    }

    public String kind() {
        return "Cat";
    }

    public String name() {
        return _name;
    }

    public int age() {
        return _age;
    }

    public String speak() {
        return "Meow! Meow!";
    }
}