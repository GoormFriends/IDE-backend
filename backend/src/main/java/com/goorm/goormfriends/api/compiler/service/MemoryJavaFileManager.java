package com.goorm.goormfriends.api.compiler.service;


import javax.tools.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, byte[]> classBytes = new HashMap<>();

    public MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            return new SimpleJavaFileObject(URI.create("mem:///" + className.replace('.', '/') + kind.extension), kind) {
                @Override
                public OutputStream openOutputStream() {
                    return new ByteArrayOutputStream() {
                        @Override
                        public void close() throws IOException {
                            classBytes.put(className, this.toByteArray());
                        }
                    };
                }
            };
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    public byte[] getClassBytes(String className) {
        return classBytes.get(className);
    }
}
