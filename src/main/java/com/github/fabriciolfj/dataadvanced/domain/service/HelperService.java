package com.github.fabriciolfj.dataadvanced.domain.service;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HelperService {

    private final AuthorRepository repository;

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistAuthor(Author author) {
        repository.save(author);
        repository.count();

        throw new RuntimeException();
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
