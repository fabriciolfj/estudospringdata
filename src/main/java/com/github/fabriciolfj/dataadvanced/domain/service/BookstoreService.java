package com.github.fabriciolfj.dataadvanced.domain.service;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.Book;
import com.github.fabriciolfj.dataadvanced.domain.repository.AuthorRepository;
import com.github.fabriciolfj.dataadvanced.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookstoreService {

    private final HelperService helperService;

    public void mainAuthor(){
        Author author = new Author();
        author.setAge(43);
        author.setGenre("Masculino");
        author.setName("Carlos");

        Book book = new Book();
        book.setAuthor(author);
        book.setPrice(new BigDecimal(10.00));
        book.setIsbn("9923232j");
        book.setTitle("Teste1");

        Book book2 = new Book();
        book.setAuthor(author);
        book.setPrice(new BigDecimal(15.00));
        book.setIsbn("9923232j");
        book.setTitle("Teste2");

        Book book3 = new Book();
        book.setAuthor(author);
        book.setPrice(new BigDecimal(17.00));
        book.setIsbn("9923232j");
        book.setTitle("Teste3");

        author.addBook(book);
        author.addBook(book2);
        author.addBook(book3);
        persistAuthor(author);
        notifyAuthor(author);
    }

    public void persistAuthor(Author author) {
        helperService.persistAuthor(author);
    }

    public void update(long id) {
        helperService.updateAuthor(id);
    }

    private void notifyAuthor(Author author) {
        log.info("Saving author: " + author);
    }

    public void longRunningServiceMethod() {
        helperService.deleteGenre();
    }
}