# IoC(Inversion of Control) 컨테이너와 빈
> 서비스가 사용할 의존객체를 직접 만들어서 사용 하는 것이 아니라  
> 어떤 장치(생성자등)을 통해서 주입을 받아서 사용하는 방법  

## 스프링 IoC 컨테이너를 사용하는 이유
> 여러 개발자들이 스프링 커뮤니티에서 논의해서 만들어낸  
> 여러가지 디펜던시 인잭션 방법과 베스트 프랙티스들의 노하우가 쌓여있는 프레임워크이기 때문  
> 이 컨테이너 안에 있는 객체들을 빈(Bean)들이라고 함  
> IoC기능을 제공하는 빈들을 담고 있기 때문에 컨테이너라고 함  
> 컨테이너로부터 빈들을 가져와서 사용할 수 있음  
> 싱글톤으로 객체를 만들어 관리하고 싶을 때도 IoC컨테이너를 사용  

## 핵심 IoC 컨테이너
- BeanFactory

## 빈(Bean)
> 스프링 IoC 컨테이너가 관리하는 객체  
> 의존성 주입을 하고 싶으면 Bean이 되어야됨  
- 장점
  - 스프링 IoC컨테이너에 등록되는 빈들은 아무런 애노테이션을 붙이지 않았다면 기본적으로 싱글톤 Scope로 등록됨
  - 메모리면에서도 효율적이고 런타임시에 성능 최적화에도 유리
- 라이프사이클 인터페이스
  > 라이프사이클 인터페이스를 사용하여 부가적인 부기능들을 만들어 낼 수 있음  
   ```java
   @PostConstruct
   public void postConstruct() {
      System.out.println("=========================");
      System.out.println("Hello");
   }
   ```

## 의존성 주입
BookRepository를 구현하지 않고서는 BookService만 테스트할 수 없는 상황  
BookService에 코드가 있지만 BookRepository는 Null을 리턴 하므로  
의존성을 가진 BookService로 단위테스트를 만들기 힘듬  
더 힘든 상황은 BookRepository 직접 만들어서 사용하는 경우 의존성을 주입해줄 수 없는 상황이 테스트하기 더 힘든 상황임  
```java
//의존성 주입이 불가능한 객체 직접 생성한 경우
private BookRepository bookRepository = new BookRepository();

//의존성 주입이 가능한 객체
private BookRepository bookRepository;

public BookService(BookRepository bookRepository) {
   this.bookRepository = bookRepository;
}
```
의존성을 주입해줄 수 있도록 되어있으므로 가짜 객체를 만들어서 의존성을 주입해서 테스트 할 수 있음  
```java
@RunWith(SpringRunner.class)
public class BookServiceTest {

    //가짜 객체 Mocking
    @Mock
    BookRepository bookRepository;

    @Test
    public void save() {
        Book book = new Book();

        //save라는 메서드가 호출될 때 book이 들어오면 book을 리턴하라
        when(bookRepository.save(book)).thenReturn(book);
        BookService bookService = new BookService(bookRepository);

        Book result = bookService.save(book);

        assertThat(book.getCreated()).isNotNull();
        assertThat(book.getBookStatus()).isEqualTo(BookStatus.DRAFT);
        assertThat(result).isNotNull();
    }
}
```

## 고전적인 bean 설정
- `name`은 setter에서 가져 온것
- `ref`는 다른 bean 의 id
- id는 첫 글자는 소문자로쓰는 camel-case 컨벤션으로 작성

## ApplicationContext
> 실질적으로 가장 많이 사용하게 되는 Bean을 담고 있는 IoC컨테이너  
> BeanFactory 를 상속 받았음  
> BeanFactory 이외에 다양한 기능들을 더 추가로 가지고 있는 인터페이스  
- ApplicationEventPublisher(이벤트 발행 기능)
- BeanFactory
- EnvironmentCapable
- HierarchicalBeanFactory
- ListableBeanFactory
- ResourceLoader(리소스 로딩 기능)
- ResourcePatternResolver
- MessageSource(메시지 소스 처리 기능(i18n 메시지 다국화))

## Annotation
> 스프링 2.5부터 가능한 사용방법 어노테이션 기반의 설정  
- @Component: 빈설정 어노테이션
- @Service: @Component를 확장받은 서비스 어노테이션
- @Repository: @Component를 확장받은 레포지토리 어노테이션
- @Autowired: 빈 의존성 주입 받음
- @Inject: 빈 의존성 주입 받음
- @Configuration: 빈 설정 파일 어노테이션

### @ComponentScan
- basePackages
  - 문자열로 패키지 명을 입력해야됨
- basePackageClasses
  - 지정된 클래스가 위치한곳 부터 Component Scanning를 해라
  - 모든 클래스에 붙어있는 Annotation들을 찾아서 Bean으로 등록해라

### @SpringBootApplication
> 스프링 부트에서 위의 설정사항을 모두 적용해놓은 어노테이션