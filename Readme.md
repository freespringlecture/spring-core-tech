# Null-safety
> 스프링 프레임워크 5에 추가된 Null 관련 애노테이션
> Spring Data 와 Reactor에서 사용함
- @NonNull
- @Nullable
- @NonNullApi (패키지 레벨 설정)
- @NonNullFields (패키지 레벨 설정)

## 목적
> Null을 허용하느냐 Null을 허용하지 않느냐를 애노테이션으로 마킹 해놓고  
> (툴의 지원을 받아) 컴파일 시점에 최대한 NullPointerException을 미연에 방지하는 것

## @NonNull 예제
- 받는 쪽 구현
```java
@Service
public class EventService {

    @NonNull
    public String createEvent(@NonNull String name) {
        return null;
    }
}
```

- 보내는 쪽 구현
```java
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    EventService eventService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        eventService.createEvent("freelife");
    }
}
```

## @NonNullApi 예제
> package-info.java 파일을 추가하고 패키지에 `@NonNullApi`를 적용하면
> 해당 패키지의 모든 파라메터와 리턴타입 `@NonNull`을 적용하는 것과 마찬가지 임
> 패키지에 전부 `@NonNull`을 적용하고 Null을 허용하는 곳에만 `@Nullable`을 붙이는 방식의 코딩이 가능함
```java
@NonNullApi
package me.freelife;

import org.springframework.lang.NonNullApi;
```


## intellij 설정 위치
위치: `Build, Excution, Deployment` -> `Compiler` -> `Configure annotations...`
1. **Nullable annotations**에 `org.springframework.lang.Nullable` 추가
2. **NotNull annotations**에 `org.springframework.lang.NonNull` 추가
3. intellij 재시작
4. Passing 'null' argument to parameter annotated as @NotNull 보내는 쪽이 null이면 이런 메세지가 보임
5. 'null' is returned by the method declared as @NonNull 받는 쪽이 null일 경우 이런 메세지가 보임