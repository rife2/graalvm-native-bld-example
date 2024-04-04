package com.example;

public class GraalNativeMain {
    public String getMessage() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new GraalNativeMain().getMessage());
    }
}