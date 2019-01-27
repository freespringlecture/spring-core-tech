## IoC 컨테이너 3부: @Autowired
### required
> @Autowired 를 처리 하다가 해당하는 빈의 타입을 못찾거나 의존성 주입을 할수 없는 경우 에러가 나고 애플리케이션 구동이 불가능함  
> 아래와 같이 설정하면 의존성 주입을 할 수없는 경우 패스함  
```java
@Autowired(required = false)
```
> 생성자를 사용한 의존성 주입에는 사용할 수 없음  
> 생성자를 사용한 의존성 주입은 빈을 만들때에도 개입이 됨   
> 생성자에 전달받아야 되는 타입의 빈이 없으면 인스턴스를 만들지 못하고 서비스도 등록이 되지 않음  

#### 사용할 수 있는 위치
  - 생성자 (스프링 4.3 부터는 생략 가능)
  - 세터
  - 필드

#### 경우의 수
  - 해당 타입의 빈이 없는 경우
  - 해당 타입의 빈이 한 개인 경우
  - 해당 타입의 빈이 여러 개인 경우
    - 빈 이름으로 시도
      - 같은 이름의 빈 찾으면 해당 빈 사용
      - 같은 이름 못 찾으면 실패

#### 같은 타입의 빈이 여러개 일 때
  - @Primary - 
  - 해당 타입의 빈 모두 주입받기 - List<Repository> bookRepositories;
  - @Qualifier(빈 이름으로 주입)
  - 그외 위의 어노테이션을 사용하지 않고 필드이름과 동일하게 지정해서 의존성을 주입하면 그이름과 동일한 레파지토리를 자동으로 주입받음
```java
@Autowired
BookRepository myBookRepository;
```

#### 동작 원리
> BeanFactory 가 자신에게 등록된 BeanPostProcessor 들을 찾아서 일반적인 Bean들에게 로직을 적용함  
> AutowiredAnnotationBeanPostProcessor 가 기본적으로 Bean으로 등록되어있음  
> BeanPostProcessor 의 라이프사이클 구현체에 의해 동작함  
> @Autowired 는 postProcessBeforeInitialization 단계  
> ApplicationRunner 의 경우 애플리케이션 구동이 다 끝나고 동작함  
> Runner을 사용하지 않으면 애플리케이션 구동 중에 처리됨  

- BeanPostProcessor
  - 아래의 세가지 라이프사이클 단계가 있다
    - postProcessBeforeInitialization
    - InitializingBean's afterPropertiesSet
    - postProcessAfterInitialization
> Bean을 Initializer(만들다)하여 인스턴스를 만든 다음에  
> Bean의 초기화 라이프사이클(Initialization LifeCycle) 이전 OR 이후에 부가적인 작업을 할 수 있는 또다른 라이프사이클 콜백  

- Initialization
- `@PostConstruct` 등의 어노태이션으로 Bean이 만들어진 이후에 해야할일을 정의 해주는것
- InitializingBean을 implement 해서 afterPropertiesSet 메서드를 구현