package com.github.fabriciolfj.dataadvanced;

import com.github.fabriciolfj.dataadvanced.domain.forkjoin.ForkJoinService;
import com.github.fabriciolfj.dataadvanced.domain.repository.BatchRepositoryImpl;
import com.github.fabriciolfj.dataadvanced.domain.repository.BookRepository;
import com.github.fabriciolfj.dataadvanced.domain.service.AuthorService;
import com.github.fabriciolfj.dataadvanced.domain.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BatchRepositoryImpl.class)
public class DataAdvancedApplication {

    private static final String FILE_NAME = "citylots.json";

    @Autowired
    private ForkJoinService forkJoinService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookstoreService bookstoreService;

    public static void main(String[] args) {
        SpringApplication.run(DataAdvancedApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            //forkJoinService.fileToDatabase(FILE_NAME);
            //authorService.batchAuthors();
            //authorService.imprimirLivros();
            bookstoreService.mainAuthor();
            bookstoreService.update();
        };
    }

}
