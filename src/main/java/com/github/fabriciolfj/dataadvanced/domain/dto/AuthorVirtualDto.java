package com.github.fabriciolfj.dataadvanced.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface AuthorVirtualDto {

    String getName();
    @Value("#{target.age}")
    String years();
    @Value("#{T(java.lang.Math).random() * 10000}")
    int rank();
    @Value("5")
    String books();
}
