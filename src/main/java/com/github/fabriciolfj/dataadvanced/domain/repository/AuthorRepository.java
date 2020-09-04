package com.github.fabriciolfj.dataadvanced.domain.repository;

import com.github.fabriciolfj.dataadvanced.domain.dto.AuthorNameAge;
import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(value = "author-books-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Author> findByAgeLessThanOrderByNameDesc(int age);

    @EntityGraph(value = "author-books-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Author> findAll(Specification spec);

    //uma abordagem para resolver a questão do carregamento preguicoso, o que estão dentro do são tratados como eager e o restante como lazy (EntityGraph.EntityGraphType.FETCH),
    // no caso do EntityGraph.EntityGraphType.LOAD, os demais são tratados conforme especificado no FetchType
    //@EntityGraph(attributePaths = {"books"}, type = EntityGraph.EntityGraphType.FETCH)
    @EntityGraph(value = "author-books-publisher-graph", type = EntityGraph.EntityGraphType.LOAD) //pode usar a opção acima tb, mas ai não precisa da  anotação na entidade
    @Query(value = "Select a From Author a where a.age > 20 and a.age < 40")
    List<Author> find20Ate40();

    @EntityGraph(value = "author-books-publisher-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<Author> findByAgeGreaterThanAndGenre(int age, String genre);

    //@Query("Select a.name, a.age From Author a where a.genre=?1 LIMIT 2") posso usar assim
    List<AuthorNameAge> findFirst2ByGenre(String genre);
}
