# IoC 컨테이너 9부: ResourceLoader
> 리소스를 읽어오는 기능을 제공하는 인터페이스

```java
ApplicationContext extends ​ResourceLoader
```

## 리소스 읽어오기
- 파일 시스템에서 읽어오기
- 클래스패스에서 읽어오기
- URL로 읽어오기
- 상대/절대 경로로 읽어오기

```java
Resource getResource(java.lang.String location)
```

```java
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.txt"); // 파일 읽어오기
        System.out.println(resource.exists()); // 파일 유무 
        System.out.println(resource.getDescription()); // 설명
        System.out.println(Files.readString(Path.of(resource.getURI()))); // URL로 읽어오기
    }
}
```