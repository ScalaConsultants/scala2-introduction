package io.scalac.scala2.immutability;

/**
 * Mutability example in Java.
 */
public class JavaBox {

    public String value;

    public JavaBox(String value) {
        this.value = value;
    }

    public void show() {
        System.out.println("JavaBox  :: value = " + this.value);
    }
}