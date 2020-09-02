package com.github.fabriciolfj.dataadvanced.domain.repository;

import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH;

@Repository
@Transactional(readOnly = true)
public interface AuthorRepository extends BatchRepository<Author, Long> {

    @QueryHints(value = @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
    @Query("SELECT DISTINCT a FROM Author a JOIN FETCH a.books b WHERE a.age > ?1")
    List<Author> findGtGivenAge(int age);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Author a SET a.age = a.age + 1, a.version = a.version + 1")
    int updateInBulk();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Author a SET a.age = a.age + 1, a.version = a.version + 1 WHERE a IN ?1")
    int updateInBulk(List<Author> authors);

    @Override
    void deleteAllInBatch(); // não usa o recurso cascade e orphanRemoval

    @Override
    void deleteInBatch(Iterable<Author> iterable); // não usa o recurso cascade e orphanRemoval e pode estourar o numero aceito no operador in do banco de dados

    @Override
    void deleteAll(Iterable<? extends Author> iterable);

    @Query("Select a From Author a where a.name = ?1")
    Author fetchByName(String name);

    @Transactional
    @Modifying
    @Query("Delete from Author a where a.genre <> ?1")
    int deleteByNeGenre(String genre);
}
