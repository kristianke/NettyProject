package com.hcns.training;

import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StreamTest {
    static Consumer<String> fun1 = new Consumer<String>() {
        @Override
        public void accept(String s) {
            System.out.println(s);
        }
    };

    static Consumer<String> fun2 = fun1::accept;
    static Consumer<String> fun3 = fun2;


    public static void main(String[] args) {
        PrintStream out = System.out;
        out.println(fun2 == fun3);
        out.println(fun2 == fun1);
        fun2 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("ABC" + s);
            }
        };

        fun2.accept("AAA");
        fun3.accept("AAA");

        Consumer<String> consumer = System.out::println;
        consumer.accept("aliens");



    }
}
