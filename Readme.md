# IoC 컨테이너 7부: MessageSource
> 국제화(i18n) 기능을 제공하는 인터페이스
ApplicationContext 에서 상속 받은 인터페이스

- MessageSource 직접설정 예시
> ReloadableResourdeBundleMessageSource 로 메세지 변경 시 변경된 메세지를 반영
```java
@Bean
public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(3); //캐싱하는 시간을 최대 3초까지만 캐싱하고 다시 읽음
    return messageSource;
}
```

## 스프링부트
> 스프링 부트를 사용한다면 별다른 설정 필요없이 아래와 같이 messages.properties 사용할 수 있음  
> 원래 빈으로 각각 등록시켜줘야 하지만 스프링 부트를 쓰면 자동으로 `ResourceBundleMessageSource` 가 빈으로 등록되어있음  
- messages.properties
```
greeting=hello, {0}
```
- messages_ko_KR.properties
```
greeting=안녕, {0}
```