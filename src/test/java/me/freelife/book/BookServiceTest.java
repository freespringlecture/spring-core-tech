package me.freelife.book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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