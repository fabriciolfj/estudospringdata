package com.github.fabriciolfj.dataadvanced.domain.repository;

import com.github.fabriciolfj.dataadvanced.domain.dto.BookDTO;
import com.github.fabriciolfj.dataadvanced.domain.entity.Author;
import com.github.fabriciolfj.dataadvanced.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

}
