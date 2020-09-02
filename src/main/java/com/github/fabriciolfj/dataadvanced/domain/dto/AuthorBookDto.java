package com.github.fabriciolfj.dataadvanced.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface AuthorBookDto {
    String getName();
    Integer getAge();
    String getTitle();
    String getIsbn();

    @JsonIgnore
    long getTotal();
}
