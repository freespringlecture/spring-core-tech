# IoC 컨테이너 4부: @Component와 컴포넌트 스캔
## 애플리케이션 구동방법
1. 기본적인 static 메서드 사용
> 싱글톤 스코프의 Bean들을 초기에 다 생성함 등록할 Bean이 많다면 구동시에 초기 구동시간이 꽤 걸림
```java
SpringApplication.run(Demospring51Application.class, args);
```
1. Builder를 활용한 방법
2. 인스턴스를 만들어 사용하는 방법
```java
var app = new SpringApplication(Demospring51Application.class);
app.run(args);
```
4. Functional 방법
> 구동 시 리플렉션이나 Proxy 같은 cg라이브러리 기법을 안쓰므로 성능상의 이점이 있음
> 하지만 일일히 Bean들을 등록해주기에는 너무 불편함 @ComponentScan을 사용하는 것에 비해 너무 불편함
- lambda 적용 전캔
- 
```java 
var app = new SpringApplication(Demospring51Application.class);
app.addInitializers(new ApplicationContextInitializer<GenericApplicationContext>() {
    @Override
    public void initialize(GenericApplicationContext ctx) {
        ctx.registerBean(MyService.class);
        ctx.registerBean(ApplicationRunner.class, new Supplier<ApplicationRunner>() {
            @Override
            public ApplicationRunner get() {
                return new ApplicationRunner() {
                    @Override
                    public void run(ApplicationArguments args) throws Exception {
                        System.out.println("Funational Bean Definition!!");
                    }
                };
            }
        });
    }

});
app.run(args);
```

- lambda 적용후
```java
var app = new SpringApplication(Demospring51Application.class);
app.addInitializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
    ctx.registerBean(MyService.class);
    ctx.registerBean(ApplicationRunner.class, () -> args1 -> System.out.println("Funational Bean Definition!!"));
});
app.run(args);
```

## 컴포넌트 스캔 주요 기능
1. 스캔 위치 설정: 어디부터 어디까지 scan할 것인가에 관한 설정
2. 필터: 어떤 애노테이션을 스캔 할 지 또는 하지 않을 지 scan하는 중에 어떤 것을 걸러낼 것 인가에 관한 설정

## Component
- @Repository
- @Service
- @Controller
- @Configuration

## @ComponentScan
> @ComponentScan은 스캔할 패키지와 애노테이션에 대한 정보
> 다른 Bean 들을 등록하기 전에 먼저 Bean을 등록 해줌
> 실제 스캐닝 ConfigurationClassPostProcess 라는 BeanFactoryPostProcessor에 의해 처리됨

- 주의 사항
  - @ComponentScan 이나 @SpringBootApplication 위치를 확인 위치에 따라 다른 패키지라면 Bean으로 등록되지않음
  - @Component 로 지정이되어있는지 확인
  - @ComponentScan 에 excludeFilters(예외)로 지정된 사항은 Bean으로 등록되지 않음