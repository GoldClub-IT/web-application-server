# 실습을 위한 개발 환경 세팅

* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트

* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리

* 구현 단계에서는 각 요구사항을 구현하는데 집중한다.
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다.

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답

* WebServer 클래스는 웹 서버를 시작하고, 사용자의 요청이 있을 때까지 대기 상태에 있다가 사용자 요청이 있을 경우 사용자의 요청을 RequestHandler 클래스에 위임하는 역할을 한다
* 사용자의 요청이 발생할 때까지 대기 상태에 있도록 지원하는 역할은 자바에 포함되어 있는 ServerSocket 클래스가 담당한다.
* WebServer 클래스는 ServerSocket에 사용자 요청이 발생하는 순간 클라이언트와 연결을 담당하는 Socket을 RequestHandler에 전달하면서 새로운 스레드를 실행하는 방식으로 멀티스레드
  프로그래밍을 지원한다.
* RequestHandler 클래스는 Thread를 상속하고 있으며, 사용자의 요청에 대한 처리와 응답에 대한 처리를 담당하는 가장 중심이 되는 클래스다.
* InputStream, OutputStream 모두 implements Closable, 따라서 try 구문 안에서 선언 이후에 close() 자동으로 된다.
* InputStream = System.in -> InputStreamReader -> BufferedReader
* byte 타입으로 읽어들이는 in을 char 타입으로 처리한 뒤 String, 즉 문자열로 저장할 수 있게 한다.
* 기본적으로 바이트 스트림인 InputStream을 통해 바이트 단위로 데이터를 입력 받는다. 또한 System.in의 타입도 InputStream이다.
* InputStreamReader은 문자(character) 단위로 데이터를 처리할 수 있도록 돕는다.
* InputStream의 데이터를 문자로 변환하는 중개 역할을 한다.
* BufferedReader은 스트림에 버퍼를 두어 문자를 버퍼에 일정 정도 저장해둔 뒤 한 번에 보낸다.
* Junit 4 -> Junit 5
    * Junit 4: 모든 것이 하나의 Jar 파일에 번들로 포함되어 있다.
    * Junit 5: Java 8 스타일의 코딩을 적용하고 Junit4보다 강력하고 유연하게 만드는 것이 목표, JUnit Platform, JUnit Jupiter 그리고 JUnit Vintage의 3가지
      하위
      프로젝트로 구성됩니다.
    * JUnit 5에서는 테스트 클래스와 테스트 메서드를 public으로 하지 않아도 됩니다. 이제 package protected로 만들 수 있습니다. JUnit은 내부적으로 리플렉션을 사용하여 테스트
      클래스와 테스트 메서드를 찾습니다. 리플렉션은 가시성이 제한된 경우에도 이를 찾을 수 있으므로 public으로 설정할 필요가 없습니다.
    * JUnit의 테스트 클래스는 파라미터도 있는 non-public 생성자를 가질 수 있습니다. 즉 JUnit 5에서는 기본 생성자가 필수가 아닙니다.
* 왜 JUnit 5에서 throws Exception을 생략해도 되는가?
    * JUnit 5의 @Test 메서드는 다음과 같이 선언되어 있습니다:

```java

@Target({METHOD})
@Retention(RUNTIME)
@Testable
public @interface Test {
}
```

즉, JUnit 5는 테스트 메서드에서 예외가 던져지면 런타임에 전파되고 실패 처리됩니다.
throws Exception을 반드시 명시할 필요는 없습니다. 컴파일러가 요구하는 경우에만 붙이면 됩니다. JUnit 5테스트 메서드에서 예외가 발생하면, 그것을 자동으로 테스트 실패로 간주합니다. 그리고
자바에서는 unchecked exception은 throws 명시가 필요 없기 때문에, 대부분의 경우 생략이 가능합니다. JUnit 5는 JUnit 4처럼 @Test(expected = ...) 방식이 아닌,
다음처럼 예외를 검증하는 API를 제공합니다:

```java

@Test
void testThrows() {
    assertThrows(IOException.class, () -> {
        throw new IOException("boom");
    });
}
```

이런 구조 때문에 throws가 굳이 필요 없는 경우가 많아졌고, 개발자가 직접 예외를 테스트할 수 있게 된 것입니다.
던지는 예외가 checked exception(예: IOException, SQLException)이라면 throws를 명시해야 합니다.

### 요구사항 2 - get 방식으로 회원가입

*

### 요구사항 3 - post 방식으로 회원가입

*

### 요구사항 4 - redirect 방식으로 이동

*

### 요구사항 5 - cookie

*

### 요구사항 6 - stylesheet 적용

*

### heroku 서버에 배포 후

* 