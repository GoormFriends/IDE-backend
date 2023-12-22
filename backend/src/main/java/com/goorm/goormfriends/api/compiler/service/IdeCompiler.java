package com.goorm.goormfriends.api.compiler.service;

import javax.tools.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IdeCompiler {

	//	메모리 내에서 코드 컴파일
//	JavaCompiler는 자바 표준 라이브러리 (자바 표준 런타임 환경 = JRE , 지금 사용한 클래스는 JDK)
	private final JavaCompiler compiler;

	//	JavaFileManager = 메모리 내의 Java 코드 컴파일된 바이트코드 관리
//	ForwardingJavaFileManager = JavaFileManager의 기능을 유지 + 필요한 추가 로직 구현
	private final MemoryJavaFileManager memoryFileManager;

	public IdeCompiler() {
//		import javax.tools.JavaCompiler 클래스는 자바 플랫폼의 일부
//		ToolProvider.getSystemJavaCompiler() 메서드를 사용해 자바를 컴파일 하는 기능
		log.trace("Initializing IdeCompiler.");
		compiler = ToolProvider.getSystemJavaCompiler();

		if (compiler == null) {
			throw new IllegalStateException("System Java Compiler not available.");
		}

//		getStandardFileManager는 표준관리자의 가장 기본적인 구현
//		null 값 부분 = 표준 파일 관리자 구성의 옵션(헤당 부분이 null이면 기본값)
		StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
		if (standardFileManager == null) {
			throw new IllegalStateException("StandardFileManager initialization failed.");
		}
		// MemoryJavaFileManager 초기화
		memoryFileManager = new MemoryJavaFileManager(standardFileManager);
		log.trace("IdeCompiler initialized successfully.");
	}

	// 컴파일 메서드: 소스 코드(= 원래는 usercode인데 매개변수 사용한 거)를 받아 컴파일하고 결과를 반환
	public Map<String, Object> compile(String sourceCode) {
//		오류메세지 저장하기 위해서 만듬
		StringWriter output = new StringWriter();
		System.out.println(sourceCode);
		boolean success = compileSource(sourceCode, output);

		// 컴파일 결과를 저장하는 맵
		Map<String, Object> result = new HashMap<>();
		if (!success) {
			result.put("error", output.toString());
		} else {
			String className = extractClassName(sourceCode);
			byte[] classBytes = memoryFileManager.getClassBytes(className);
			MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
			memoryClassLoader.addClass(className, classBytes);
			try {
				result.put("class", memoryClassLoader.loadClass(className));
			} catch (ClassNotFoundException e) {
				result.put("error", "Class loading failed: " + e.getMessage());
			}
		}

		//컴파일 결과를 담은 맵 반환
		return result;
	}


	//	실제 컴파일 작업 성공여부 (자바소스코드 매개변수, 오류메세지 매개변수)
	private boolean compileSource(String sourceCode, StringWriter output) {

//		JavaFileObject = 소스코드를 읽고 처리하기 위한 객체
		JavaFileObject sourceFile = createSourceFile(sourceCode);

//		오류출력 하려고 getTask에서 output 넣어 사용
//	    fileManager: 컴파일러가 파일을 읽고 쓰는 방식을 관리
//      null: 컴파일 옵션, 주석 처리기, 로더 (사용하지 않아서 null처리)
//		call: 컴파일 테스트 실행
		return compiler.getTask(output, memoryFileManager, null, null, null, Collections.singletonList(sourceFile)).call();
	}

	// 가상의 Java 소스 파일 객체를 생성하는 메서드
	private JavaFileObject createSourceFile(String sourceCode) {
		String className = extractClassName(sourceCode);
		String fileName = className + ".java";

		System.out.println("filename = " + fileName);
		return new SimpleJavaFileObject(URI.create("string:///" + fileName), JavaFileObject.Kind.SOURCE) {
			@Override
			public CharSequence getCharContent(boolean ignoreEncodingErrors) {
				return sourceCode;
			}
		};
	}

	// 이 메서드는 public 또는 protected로 선언되어야 합니다.
	public String extractClassName(String sourceCode) {
		Matcher matcher = Pattern.compile("public\\s+class\\s+([\\w]+)\\s*\\{?").matcher(sourceCode);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	// MemoryClassLoader 클래스가 필요한 경우
	class MemoryClassLoader extends ClassLoader {
		private final Map<String, byte[]> classBytes = new HashMap<>();

		public void addClass(String name, byte[] bytes) {
			classBytes.put(name, bytes);
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			byte[] bytes = classBytes.get(name);
			if (bytes == null) {
				throw new ClassNotFoundException(name);
			}
			return defineClass(name, bytes, 0, bytes.length);
		}
	}

			// fileManager를 사용하여 클래스 로더를 얻는다.
			// getClassLoader는 클래스를 로딩하는데 사용되는 클래스 로더 객체를 반환한다.
			// 여기서 클래스 로더란 클래스를 가상머신이 이해할 수 있는 바이트 코드로 변환하는 것을 의미한다.
			// (null = 특정 클래스 로딩 위치를 지정하지 않음)


}
