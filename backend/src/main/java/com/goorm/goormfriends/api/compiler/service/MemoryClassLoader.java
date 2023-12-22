package com.goorm.goormfriends.api.compiler.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 사용자 정의 클래스 로더
public class MemoryClassLoader extends ClassLoader {
    private final Map<String, byte[]> compiledClasses = new ConcurrentHashMap<>();

    public void addClass(String name, byte[] bytes) {
        compiledClasses.put(name, bytes);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = compiledClasses.get(name);
        if (bytes == null) {
            throw new ClassNotFoundException(name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}