package com.github.fabriciolfj.dataadvanced.domain.service;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.Book;
import com.github.fabriciolfj.dataadvanced.domain.repository.AuthorRepository;
import com.github.fabriciolfj.dataadvanced.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Transactional
    public void batchAuthors() {
        List<Author> authors = new ArrayList<>();
        for(int i = 0; i < 5; i ++) {
            Author author = new Author();
            author.setAge(i + 1);
            author.setName("Name_" + i);
            author.setGenre("Genre_" + i);
            author.setAge(18 + i);

            /*for (int j = 0; j < 5; j++) {
                Book book = new Book();
                book.setTitle("Title: " + j);
                book.setIsbn("Isbn: " + j);

                author.addBook(book);
            }*/

            authors.add(author);
        }

        repository.saveInBatch(authors);
    }

    @Transactional(readOnly = true)
    public void imprimirLivros() {
        List<Book> books = bookRepository.findAll();

        for(Book b : books){
            System.out.println("book: " + b.getTitle());
            System.out.println("author: " + b.getAuthor());
        }
    }

    @Transactional
    public void updateAuthorsGtAgeAndBooks() {

        List<Author> authors = repository.findGtGivenAge(40);

        repository.updateInBulk(authors);
        //bookRepository.updateInBulk(authors);
        //repository.deleteAllInBatch();
        //repository.deleteInBatch(authors);
        repository.deleteAll(authors); //se beneficia do bloqueio optimista e usa a coluna version para previnir dados não atualizados.
    }
}
