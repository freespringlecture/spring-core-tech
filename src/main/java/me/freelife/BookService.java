package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;

public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
