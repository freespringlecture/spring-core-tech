# Validation 추상화
`org.springframework.validation.Validator`
> 애플리케이션에서 사용하는 객체 검증용 인터페이스

## 특징
- 어떤한 계층과도 관계가 없다. => 모든 계층(웹, 서비스, 데이터)에서 사용해도 좋다.
- 구현체 중 하나로, JSR-303(Bean Validation 1.0)과 JSR-349(Bean Validation 1.1)을 지원한다
  - LocalValidatorFactoryBean​
  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/beanvalidation/LocalValidatorFactoryBean.html
- DataBinder에 들어가 바인딩 할 때 같이 사용되기도 한다.

## 인터페이스
> 구현해야 되는 두가지 메서드
- boolean supports(Class clazz): 어떤 타입의 객체를 검증할 때 사용할 것인지 결정함
- void validate(Object obj, Errors e): 실제 검증 로직을 이 안에서 구현
  - 구현할 때 ValidationUtils 사용하며 편리 함.

## 스프링 부트 2.0.5 이상 버전을 사용할 때
- LocalValidatorFactoryBean ​​빈으로 자동 등록
- JSR-380(Bean Validation 2.0.1) 구현체로 hibernate-validator 사용.
- https://beanvalidation.org/
  - 자바 표준 JEE 스펙
  - NotEmpty, NotNull, NotBlank, Email, Size ... 애노테이션들로 빈의 데이터를 검증할 수 있는 기능

## 에러코드
만든 에러코드 외에 다른 에러코드들을 Validatior가 추가 해줌
```bash
notempty.event.title
notempty.title
notempty.java.lang.String
notempty
```

## 애노테이션 검증
> 아래와 같이 간단하게 애노테이션 검증이 가능하며
> 복잡한 검증이 필요하다면 validate를 직접 구현하여 검증하면 됨
```java
public class Event {
    Integer id;

    @NotEmpty
    String title;

    @NotNull @Min(0)
    Integer limit;

    @Email
    String email;
}
```

```bash
===== error code =====
Min.event.limit
Min.limit
Min.java.lang.Integer
Min
반드시 0보다 같거나 커야 합니다.
===== error code =====
NotEmpty.event.title
NotEmpty.title
NotEmpty.java.lang.String
NotEmpty
반드시 값이 존재하고 길이 혹은 크기가 0보다 커야 합니다.
===== error code =====
Email.event.email
Email.email
Email.java.lang.String
Email
이메일 주소가 유효하지 않습니다.
```