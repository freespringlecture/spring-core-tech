package me.freelife;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class ApplicationConfig {

    /*
    @Bean
    public BookRepository bookRepository() {
        return new BookRepository();
    }

    @Bean
    public BookService bookService() {
        // 의존성 주입을 직접하지 않고 Autowired로 주입
        return new BookService();
        *//*
        BookService bookService = new BookService();
        //setter를 사용해 의존성을 직접 주입
        bookService.setBookRepository(bookRepository());
        return bookService;
        *//*

    }
    */
}
