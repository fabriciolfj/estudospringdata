package com.github.fabriciolfj.dataadvanced.domain.dto;

public interface BookDTO {

    String getTitle();
    AuthorDto2 getAuthor();

    interface AuthorDto2 {
        String getName();
        String getGenre();
    }

}
