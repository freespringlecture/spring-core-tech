# IoC 컨테이너 8부: ApplicationEventPublisher
> 이벤트 프로그래밍에 필요한 인터페이스 제공 ​옵저버 패턴​ 구현체

```java
ApplicationContext extends ​ApplicationEventPublisher
publishEvent(ApplicationEvent event)
```

## 이벤트 만들기
- ApplicationEvent 상송
- 스프링 4.2 부터는 이 클래스를 상속받지 않아도 이벤트로 사용할 수 있다.

## 이벤트 발생 시키는 방법
- ApplicationEventPublisher.publishEvent();

## 이벤트 처리하는 방법
- ApplicationListener<이벤트> 구현한 클래스 만들어서 빈으로 등록하기.
- 스프링 4.2 부터는 ​@EventListener​를 사용해서 빈의 메소드에 사용할 수 있다.
- 기본적으로는 synchronized.
- 순서를 정하고 싶다면 @Order와 함께 사용.
- 비동기적으로 실행하고 싶다면 @Async와 함께 사용.

## 스프링이 제공하는 기본 이벤트
- ContextRefreshedEvent: ApplicationContext를 초기화 했더나 리프래시 했을 때 발생
- ContextStartedEvent: ApplicationContext를 start()하여 라이프사이클 빈들이 시작 
  - 신호를 받은 시점에 발생
- ContextStoppedEvent: ApplicationContext를 stop()하여 라이프사이클 빈들이 정지
  - 신호를 받은 시점에 발생.
- ContextClosedEvent: ApplicationContext를 close()하여 싱글톤 빈 소멸되는 시점에 발생
- RequestHandledEvent: HTTP 요청을 처리했을 때 발생

> 스프링 4.2 부터는 ApplicationEvent 클래스를 상속받지 않아도 이벤트로 사용할 수 있다
- 4.2이전 상속을 받아 Event를 정의하는 로직
```java
public class MyEvent extends ApplicationEvent {

    private int data;
    /**
     * Create a new ContextStartedEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, int data) {
        super(source);
        this.data = data;
    }

    public int getData() {
        return data;
    }

}
```
- 4.2 이후 Event 정의 로직
> 스프링이 추구하는 스프링 프레임웍 코드가 전혀 들어가지 않은 POJO 기반의 프로그래밍의 비침투성 로직
> 더 편하고 더 코드를 유지보수하기 쉬워짐
> 이벤트는 빈이 아님
```java
public class MyEvent {

    private int data;
    private Object source;

    public MyEvent(Object source, int data) {
        this.source = source;
        this.data = data;
    }

    public Object getSource() {
        return source;
    }

    public int getData() {
        return data;
    }

}
```


### ApplicationEventPublisher
> Event를 발생시키는 로직
```java
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ApplicationEventPublisher publisherEvent;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        publisherEvent.publishEvent(new MyEvent(this, 100));
    }
}
```

### ApplicationListener
> Event를 받아서 처리하는 Handler 구현
- 4.2 이전 로직
```java
@Component
public class MyEventHandler implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("이벤트 받았다. 데이터는 " + event.getData());
    }
}
```

- 4.2 이후 로직
```java
@Component
public class MyEventHandler {

    @EventListener
    public void handle(MyEvent event) {
        System.out.println("이벤트 받았다. 데이터는 " + event.getData());
    }
}
```

### Order
> Event 실행 순서 지정
> `@Order(Ordered.HIGHEST_PRECEDENCE)` 라고 설정하면 가장 먼저 실행됨 
> `@Order(Ordered.HIGHEST_PRECEDENCE + 2)` 라고 설정하면 조금 더 늦게 실행됨 
```java
@Component
public class MyEventHandler {

    @EventListener
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void handle(MyEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("이벤트 받았다. 데이터는 " + event.getData());
    }
}
```

### @EnableAsync
> 비동기 설정 @EnableAsync 를 설정하면 메인 Thread 가 아닌 별도의 Thread를 생성하여 수행
```java
@SpringBootApplication
@EnableAsync
public class Ioccontainer8Application {

    public static void main(String[] args) {
        SpringApplication.run(Ioccontainer8Application.class, args);
    }

}
```

- Handler 로직에 @Async 설정
```java
@Component
public class MyEventHandler {

    @EventListener
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Async
    public void handle(MyEvent event) {
        System.out.println(Thread.currentThread().toString());
        System.out.println("이벤트 받았다. 데이터는 " + event.getData());
    }
}
```

### LifeCycle Event
- ContextRefreshedEvent: ApplicationContext를 초기화 했거나 리프래시 했을 때 발생.
- ContextStartedEvent: ApplicationContext를 start()하여 라이프사이클 빈 들이 시작신호를 받은시점에 발생.
- ContextStoppedEvent: ApplicationContext를 stop()하여 라이프사이클 빈 들이 정지신호를 받은시점에 발생.
- ContextClosedEvent: ApplicationContext를 close()하여 싱글톤 빈 소멸 되는시점에 발생.
- RequestHandledEvent: HTTP 요청을 처리 했을 때 발생.
