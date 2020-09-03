package com.github.fabriciolfj.dataadvanced.domain.service;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.AuthorId;
import com.github.fabriciolfj.dataadvanced.domain.entity.Publisher;
import com.github.fabriciolfj.dataadvanced.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HelperService {

    private final AuthorRepository repository;

    public void deleteById(AuthorId authorId) {
        repository.deleteById(authorId);
        System.out.println(repository.count());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistAuthor(Author author) {
        repository.save(author);
        repository.count();
    }

    @Transactional
    public void updateAuthor(Publisher publisher) {
        publisher.setCompany("Teste");
        var author = repository.findById(new AuthorId("Carlos", 43, publisher)).orElseThrow();
        author.setGenre("Teste");
    }

    @Transactional
    public void delete(AuthorId authorId) {
        repository.deleteById(authorId);
    }

    @Transactional
    public void deleteGenre() {
       /*var author = repository.fetchByName("Name_1");
        repository.deleteByNeGenre(author.getGenre());*/
    }

    @Transactional(readOnly = true)
    public void fetchName() {
        repository.fetchName("Fabricio").stream().forEach(System.out::println);
    }
}
