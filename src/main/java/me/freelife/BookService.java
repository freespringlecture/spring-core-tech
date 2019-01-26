package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BookService {

    // AutowiredAnnotationBeanPostProcessor 가 해당하는 빈을 찾아서 의존성을 주입해줌
    // Initializer 전에 해줌
    @Autowired
    BookRepository myBookRepository;


    // Runner는 애플리케이션이 구동되고 찍지만 라이프사이클 PostConstruct는 구동중에 찍음
    @PostConstruct
    public void setup() {
        System.out.println(myBookRepository.getClass());
    }

}
