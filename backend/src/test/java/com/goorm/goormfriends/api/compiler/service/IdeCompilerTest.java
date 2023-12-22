package com.goorm.goormfriends.api.compiler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdeCompilerTest {

    private final IdeCompiler compiler = new IdeCompiler();

    @Test
    public void testClassNameExtraction() {
        assertEquals("MyClass", compiler.extractClassName("public class MyClass {"));
        assertEquals("MyClass", compiler.extractClassName("public class MyClass{}"));
        assertEquals("MyClass", compiler.extractClassName("public    class   MyClass    {"));
        assertEquals("MyClass", compiler.extractClassName("public class MyClass"));
        assertEquals("MyClass", compiler.extractClassName("public class MyClass extends OtherClass {"));
        Assertions.assertNull(compiler.extractClassName("class MyClass {")); // public이 없는 경우
        Assertions.assertNull(compiler.extractClassName("public interface MyClass {")); // 클래스가 아닌 인터페이스인 경우
    }
}
