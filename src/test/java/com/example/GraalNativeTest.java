package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraalNativeTest {
    @Test
    void verifyHello() {
        assertEquals("Hello World!", new GraalNativeMain().getMessage());
    }
}
