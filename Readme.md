# IoC 컨테이너 2부: ApplicationContext와 다양한 빈 설정 방법
> 빈 설정 -> 스프링 IoC 컨테이너

## 고전적인 Application 설정
```xml
<?xml version="1.0" encoding="utf-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd" >

    <bean id="bookService" class="me.freelife.BookService"> <!-- bookService 빈 등록 -->
        <!-- bookRepository 프로퍼티에 bookRepository 빈을 레퍼런스로 주입  -->
        <!-- bookRepository name은 setter에서 가지고 온 것 -->
        <!-- ref는 레퍼런스로 다른 빈을 참조한다는 뜻 -->
        <!-- ref에는 setter 에 들어갈 수 있는 다른 bean의 id가 와야됨 -->
        <property name="bookRepository" ref="bookRepository"/>
    </bean>

    <bean id="bookRepository" class="me.freelife.BookRepository"/> <!-- bookRepository 빈 등록 -->

</beans>
```

- BookRepository
```java
public class BookRepository {
}
```

- BookService
```java
public class BookService {

    BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
```

- application
```java
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanDefinitionNames));
        BookService bookService = (BookService) context.getBean("bookService");
        System.out.println(bookService.bookRepository != null);
    }

}
```

### 일일히 빈으로 등록하는 단점을 보완하기 위한 방법
> 단점을 보완하기위해 패키지를 스캔해서 @Component @Service @Repository 처럼  
> @Component를 확장한 애노테이션들을 스캐닝해서 빈으로 자동으로 등록해줌  
> 이렇게 등록된 빈은 @Autowired 나 @Inject를 통해 의존성을 주입하여 사용  
> 애너테이션 기반에 빈을 등록하고 설정하는 기능은 스프링 2.5부터 가능한기능  
```xml
<!-- @Compnent @Service @Repository 애노테이션을 스캐닝 해서 빈으로 등록 해줌 -->
<context:component-scan base-package="me.freelife"/>
```

- BookRepository
```java
@Repository
public class BookRepository {
}
```

- BookService
```java
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
```

## java 설정 파일
> 빈 설정 파일을 xml이 아닌 java로 설정하는 파일
```java
@Configuration
public class ApplicationConfig {

    @Bean
    public BookRepository bookRepository() {
        return new BookRepository();
    }

    @Bean
    public BookService bookService() {
        BookService bookService = new BookService();
        bookService.setBookRepository(bookRepository());
        return bookService;
    }
}
```

- BookRepository
```java
public class BookRepository {
}
```

- BookService
```java
public class BookService {

    BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
```

- application
```java
public class Application {

    public static void main(String[] args) {
        //ApplicationConfig 를 빈 설정으로 사용하겠다는 설정
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        // ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanDefinitionNames));
        BookService bookService = (BookService) context.getBean("bookService");
        System.out.println(bookService.bookRepository != null);
    }

}
```

### ComponentScan
> Application.class 가 있는 곳에서 ComponentScan하여 빈으로 등록
```java
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class ApplicationConfig {

}
```

## 현재의 방식
> 스프링 부트에서 사용하는 방식 @ComponentScan과 다수의 설정이 포함되어 있음
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

}
```

## 스프링 IoC 컨테이너의 역할
- 빈 인스턴스 생성
- 의존 관계 설정
- 빈 제공

## AppcliationContext
- ClassPathXmlApplicationContext (XML)
- AnnotationConfigApplicationContext (Java)

## 빈 설정
- 빈 명세서
- 빈에 대한 정의를 담고 있다
  - 이름
  - 클래스
  - 스코프
  - 생성자 아규먼트 (constructor)
  - 프로퍼트 (setter)
  - ..

## 컴포넌트 스캔
- 설정방법
  - XML 설정에서는 context:component-scan
  - 자바 설정에서 @ComponentScan
- 특정 패키지 이하의 모든 클래스 중에 @Component 애노테이션을 사용한 클래스를 빈 으로 자동으로 등록 해 줌