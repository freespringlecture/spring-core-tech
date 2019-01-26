# IoC 컨테이너 6부: Environment 1부. 프로파일
프로파일과 프로퍼티를 다루는 인터페이스
```java
ApplicationContext extends ​EnvironmentCapable
getEnvironment()
```

## profile
- 빈들의 묶음
- 환경 설정(dev, prod, staging, alpha, beta, gamma...)
  - 각각의 환경에따라 다른 빈들을 사용해야되는 경우
  - 특정 환경에서만 어떠한 빈을 등록해야되는 경우
  - Environment​의 역할은 활성화할 프로파일 확인 및 설정

## 프로파일 유즈케이스
- 테스트 환경에서는 A라는 빈을 사용하고 배포 환경에서는 B라는 빈을 쓰고 싶다
- 이 빈은 모니터링 용도니까 테스트할 때는 필요가 없고 배포할 때만 등록이 되면 좋겠다

## 프로파일 정의하기
- 클래스에 정의
  - @Configuration @Profile(“test”)
  - @Component @Profile(“test”)
- 메소드에 정의
  - @Bean @Profile(“test”)

## 프로파일 설정하기
- -Dspring.profiles.avtive=”test,A,B,...”
- @ActiveProfiles​ (테스트용)

## 프로파일 표현식
- ! (not)
- & (and)
- |(or)

- 특정 환경(test)에서의 예시
  - Ultimate
  > intelliJ - Edit Configuration - Environment - Active profiles 에 test 입력

  - Community
  > intelliJ - Edit Configuration - Environment - VM option 에 `-Dspring.profiles.active="test"` 입력

- Configuration 으로 프로파일 설정하는 방법
```java
@Configuration
@Profile("test")
public class TestConfiguration {
    @Bean
    public BookRepository bookRepository() {
        return new TestBookRepository();
    }

}
```

- Repository 에 직접 설정
> Configuration으로 프로파일을 설정하는 방법은 번거로우므로 아래와 같이 설정하면 간편함
```java
@Repository
@Profile("test")
public class TestBookRepository implements BookRepository {

}
```

- !(not), &(and), |(or)
> 아래 코드는 prod가 아니면 빈으로 등록 시킴
```java
@Repository
@Profile("!prod & test")
public class TestBookRepository implements BookRepository {

}
```