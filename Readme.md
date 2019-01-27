# Resource 추상화
`org.springframework.core.io.Resource`

## 특징
- java.net.URL을 추상화 한 것.
- 스프링 내부에서 많이 사용하는 인터페이스.

## 추상화 한 이유
- 클래스패스 기준으로 리소스 읽어오는 기능 부재
- ServletContext를 기준으로 상대 경로로 읽어오는 기능 부재
- 새로운 핸들러를 등록하여 특별한 URL 접미사를 만들어 사용할 수는 있지만 구현이 복잡하고 편의성 메소드가 부족하다

## 인터페이스 둘러보기
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/Resource.html
- 상속 받은 인터페이스
- 주요메소드
  - getInputStream()
  - exitst()
  - isOpen()
  - getDescription(): 전체 경로 포함한 파일 이름 또는 실제 URL

## 구현체
https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
- UrlResource: ​java.net.URL​ 참고, 기본으로 지원하는 프로토콜 http, https, ftp, file, jar
- ClassPathResource: 지원하는 접두어 classpath:
- FileSystemResource
- ServletContextResource: 웹 애플리케이션 루트에서 상대 경로로 리소스 찾는다
- ...

## 리소스 읽어오기
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#resources-resourceloaderaware
- Resource의 타입은 locaion 문자열과 ​**ApplicationContext의 타입**에​​ 따라 결정 된다
  - ClassPathXmlApplicationContext -> ClassPathResource
  - FileSystemXmlApplicationContext -> FileSystemResource
  - WebApplicationContext -> ServletContextResource

- ApplicationContext의 타입에 상관없이 리소스 타입을 강제하려면 java.net.URL 접두어(+ classpath:)중 하나를 사용할 수 있다
  > 명시적이므로 접두어를 사용하는 것을 권장  
  > 접두어를 사용하지 않으면 ServletContextResource로 Resolve 되므로 주의해야 함  
  > 루트는 `///`를 사용 `와일드 카드`나 `classpath*` 이렇게 사용할 수도 있음  
  - classpath:​​me/whiteship/config.xml -> ClassPathResource
  - file://​​/some/resource/path/config.xml -> FileSystemResource