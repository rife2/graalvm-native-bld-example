package com.example;

public class GraalNativeMain {
    public static void main(String[] args) {
        System.out.println(new GraalNativeMain().getMessage());
    }

    public String getMessage() {
        return "Hello World!";
    }
}