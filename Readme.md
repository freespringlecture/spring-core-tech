# 스프링 AOP: @AOP
> 애노테이션 기반의 스프링 @AOP

## 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

## 애스팩트 정의
- @Aspect
- 빈으로 등록해야 하니까 (컴포넌트 스캔을 사용한다면) @Component도 추가

## 포인트컷 정의
- @Pointcut(표현식)
- 주요표현식
  - execution
  - @annotation
  - bean
- 포인트컷 조합
  - &&, ||, !

## 어드바이스 정의
- @Before
- @AfterReturning
- @AfterThrowing
- @Around

## 참고
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-pointcuts

## 적용
### 1단계 Aspect 클래스 생성
> PerfAspect 클래스 생성 하고 @Component @Aspect 지정 및 ProceedingJoinPoint 받는 메서드 작성  
- ProceedingJoinPoint: JoinPoint가 적용될 메서드
  > 메서드를 실행하는 것 자체가 Proceed  
  > 타겟에 해당하는 메서드를 요청하고 결과값을 던져줌  
  > 아무런 부가적인 기능이 일어나지 않은 상태  
```java
@Component
@Aspect
public class PerfAspect {

    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = pjp.proceed();
        return retVal;
    }
}
```

### 2단계 부가기능 정의
> 이 Advisor를 어떻게 적용할 것인 가  
> 클래스와 클라이언트 코드를 건드리지 않았지만 부가적인 기능을 중복 코드 없이 여러 클래스에 적용함  
- Around
  > 메서드를 감싸는 형태로 적용이 됨  
  > 메서드 호출 자체를 감싸고 있기 때문에 메서드 호출 이전에도 무언가를 할 수 있고  
  > 메서드 호출 이후에도 무언가를 할 수 있고 발생한 에러를 잡아서 에러가 났을 때 특정한 일을 할 수도 있음  
  > 굉장히 다용도로 쓰일 수 있는 어노태이션  
  - execution: 어디에 적용하겠다는 Pointcut 표현식을 사용해 어디에 적용할 지를 적용할 수 있음
```java
@Component
@Aspect
public class PerfAspect {

    // me.freelife로 시작하는 package 밑에 있는 모든 클래스 중에서 EventService 안에 들어있는 모든 메서드에
    // 정의한 행위를 적용하라라는 의미 Pointcut을 여러 어드바이저에서 재사용할 것이 아니라면 이렇게 사용해도 됨
    @Around("execution(* me..*.EventService.*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
```

- 해당 패키지의 모든 클래스에 적용하는 설정
```java
@Around("execution(* me..*.*(..))") 
```

### 3단계 애노테이션 기반 설정
> 원하지 않는 메서드에까지 부가기능이 적용됨  
- RetentionPolicy: 정보를 얼마나 유지 할 것인가  
  - 애노테이션 기반으로 정의할 때 `RetentionPolicy`를 `CLASS`이상으로 주어야 함  
  - 클래스 파일까지 유지 하겠다는 의미  
  - `SOURCE`는 컴파일 후에 사라지고 `RUNTIME` 까지 유지할 필요는 없음  
  - 기본 값이 `CLASS`이므로 그냥 기본값으로 두고 사용  
```java
@Documented //javaDoc 만들때 Documention이 되도록 지정
@Target(ElementType.METHOD) //target은 Method
@Retention(RetentionPolicy.CLASS)
public @interface PerLogging {
}
```

- annotation 기반으로 변경
> 여러 곳에 흩어져 있는 기능에 각각 원하는 곳에 부가기능을 적용하고 싶다면  
> execution으로 적용하기 보다 annotation 기반으로 적용하는 것을 권장  
> 툴에서 지원을 못받는 경우 Annotation만 봐도 어느 정도 짐작이 되므로  
```java
@Component
@Aspect
public class PerfAspect {

    //annotation 기반으로 PerfLogging 애노테이션이 있는곳에 부가기능을 적용
    @Around("@annotation(me.freelife.PerfLogging)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
```

- bean에 적용도 가능함
```java
@Around("bean(simpleEventService)")
```

- 모든 메서드 실행 이전에 메시지 찍도록 하려면
```java
/**
  * 모든 메서드 실행 이전에 메세지가 찍힘
  */
@Before("bean(simpleEventService)")
public void hello() {
    System.out.println("hello");
}
```