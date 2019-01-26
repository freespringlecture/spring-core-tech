# IoC 컨테이너 6부: Environment 2부. 프로퍼티
## property
- 다양한 방법으로 정의할 수 있는 설정값
- Environment의 역할은 프로퍼티 소스 설정 및 프로퍼티 값 가져오기

### 우선순위
> StandardServletEnvironment의 우선순위
- ServletConfig 매개변수
- ServletContext 매개변수
- JNDI (java:comp/env/)
- JVM 시스템 프로퍼티 (-Dkey="value")
- JVM 시스템 환경변수 (운영체제 환경변수)

- VM option에 주는 법
```
-Dapp.name=spring5
```

- properties 파일로 설정하는법
1. resources 에 app.properties 파일 생성
```
app.about=spring
```
2. @Configuration or @SpringBootApplication 설정 파일이 있는 곳에 설정
```java
@PorpertySource("classpath:/app.properties")
```
3. Property 값 가져오기
```java
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment environment = ctx.getEnvironment();
        System.out.println(environment.getProperty("app.name"));
        System.out.println(environment.getProperty("app.about"));
    }
}
```

### 스프링 부트에서 Property 가져오기
```java
@Value("${app.name}")
String appName;
```