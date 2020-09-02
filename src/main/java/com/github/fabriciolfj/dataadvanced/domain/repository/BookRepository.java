package com.github.fabriciolfj.dataadvanced.domain.repository;

import com.github.fabriciolfj.dataadvanced.domain.dto.BookDTO;
import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("Delete from Book b where b.author.id = ?1")
    int deleteByAuthorIdentifier(final Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("Delete from Book b where b.author in ?1")
    int deleteBullByAuthors(final List<Author> authors);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("Delete from Book b where b.author.id in ?1")
    int deleteBullByAuthorsIds(final List<Long> ids);

    List<BookDTO> findBy();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Book b SET b.isbn='None', b.version=b.version + 1")
    int updateInBulk();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE Book b SET b.isbn='None', b.version = b.version + 1 WHERE b.author IN ?1")
    int updateInBulk(List<Author> authors);
}
