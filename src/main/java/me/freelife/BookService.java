package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class BookService {

    /*
    @Autowired
    @Qualifier("freelifeBookRepository")
    BookRepository bookRepository;
    */

    // 전부 다 받아오기
    /*
    @Autowired
    List<BookRepository> bookRepositories;
    */

    // AutowiredAnnotationBeanPostProcessor 가 해당하는 빈을 찾아서 의존성을 주입해줌
    // Initializer 전에 해줌
    @Autowired
    BookRepository myBookRepository;

    /*
    public void printBookRepository() {
        // System.out.println(bookRepository.getClass());
        // this.bookRepositories.forEach(System.out::println);
        System.out.println(myBookRepository.getClass());
    }
    */

    // Runner는 애플리케이션이 구동되고 찍지만 라이프사이클 PostConstruct는 구동중에 찍음
    @PostConstruct
    public void setup() {
        System.out.println(myBookRepository.getClass());
    }

    // Setter로 주입하는 방법
    /*
    @Autowired(required = false)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    */

    // 생성자로 주입하는 방법
    /*
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    */
}
