package com.github.fabriciolfj.dataadvanced.domain.service;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.Book;
import com.github.fabriciolfj.dataadvanced.domain.repository.AuthorRepository;
import com.github.fabriciolfj.dataadvanced.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class HelperService {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistAuthor(Author author) {
        Author save = repository.save(author);
        repository.count();
    }

    @Transactional(readOnly = true)
    public void getBooks() {
        /*bookRepository.findAll().stream().forEach(b ->  {
            System.out.println("Book: " + b.getId() + " Preco: " + b.getPrice() + " Desconto: " + b.getDesconto());
        });*/
        Book book = bookRepository.findById(1L).orElseThrow();
        System.out.println(book);
        System.out.println(book.getNextBook());
    }

    @Transactional
    public void updateAuthor(long id) {
        var author = repository.findById(id).orElseThrow();
        author.setGenre("Teste");
    }

    @Transactional
    public void deleteGenre() {
        var author = repository.fetchByName("Name_1");
        repository.deleteByNeGenre(author.getGenre());
    }
}
