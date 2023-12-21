package com.goorm.goormfriends.api.compiler.service;

import javax.tools.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class IdeCompiler {

	//	메모리 내에서 코드 컴파일
//	JavaCompiler는 자바 표준 라이브러리 (자바 표준 런타임 환경 = JRE , 지금 사용한 클래스는 JDK)
	private final JavaCompiler compiler;

	//	JavaFileManager = 메모리 내의 Java 코드 컴파일된 바이트코드 관리
//	ForwardingJavaFileManager = JavaFileManager의 기능을 유지 + 필요한 추가 로직 구현
	private final JavaFileManager fileManager;

	public IdeCompiler() {
//		import javax.tools.JavaCompiler 클래스는 자바 플랫폼의 일부
//		ToolProvider.getSystemJavaCompiler() 메서드를 사용해 자바를 컴파일 하는 기능
		compiler = ToolProvider.getSystemJavaCompiler();

//		getStandardFileManager는 표준관리자의 가장 기본적인 구현
//		null 값 부분 = 표준 파일 관리자 구성의 옵션(헤당 부분이 null이면 기본값)
		fileManager = compiler.getStandardFileManager(null, null, null);
	}

	// 컴파일 메서드: 소스 코드(= 원래는 usercode인데 매개변수 사용한 거)를 받아 컴파일하고 결과를 반환
	public Map<String, Object> compile(String sourceCode) {
//		오류메세지 저장하기 위해서 만듬
		StringWriter output = new StringWriter();

		boolean success = compileSource(sourceCode, output);

		// 컴파일 결과를 저장하는 맵
		Map<String, Object> result = new HashMap<>();
		if (!success) {
			result.put("error", output.toString());
		} else {
			result.put("class", loadCompiledClass());
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
		return compiler.getTask(output, fileManager, null, null, null, Collections.singletonList(sourceFile)).call();
	}

	// 가상의 Java 소스 파일 객체를 생성하는 메서드
	private JavaFileObject createSourceFile(String sourceCode) {
		Pattern pattern = Pattern.compile("public\\s+class\\s+([\\w]+)\\s*\\{?");
		Matcher matcher = pattern.matcher(sourceCode);
		String className = "UserCode";
		if (matcher.find()) {
			// 사용자가 제공한 클래스 이름을 사용
			className = matcher.group(1);
		}
		String fileName = className + ".java";
		return new SimpleJavaFileObject(URI.create("string:///" + fileName), JavaFileObject.Kind.SOURCE) {
			@Override //getCharContent: 파일 내용 문자열으로 반환
			public CharSequence getCharContent(boolean ignoreEncodingErrors) {
				return sourceCode;
			}

		};
	}

	// 컴파일된 클래스를 로드하는 메서드
	private Class<?> loadCompiledClass() {
		try {
			// fileManager가 null이 아닌지 확인
			if (fileManager == null) {
				throw new IllegalStateException("FileManager가 초기화되지 않았습니다.");
			}

			// fileManager를 사용하여 클래스 로더를 얻는다.
			// getClassLoader는 클래스를 로딩하는데 사용되는 클래스 로더 객체를 반환한다.
			// 여기서 클래스 로더란 클래스를 가상머신이 이해할 수 있는 바이트 코드로 변환하는 것을 의미한다.
			// (null = 특정 클래스 로딩 위치를 지정하지 않음)
			ClassLoader classLoader = fileManager.getClassLoader(null);
			// classLoader가 null이 아닌지 확인
			if (classLoader == null) {
				throw new IllegalStateException("클래스 로더를 가져오는 데 실패했습니다.");
			}

			// 문자열로 제공된 클래스 UserCode를 로드한다.
			return classLoader.loadClass("UserCode");
		}
		// 요청된 클래스를 찾을 수 없는 경우 예외 처리
		catch (ClassNotFoundException e) {
			throw new IllegalStateException("클래스 로드에 실패했습니다", e);
		}
	}
}
