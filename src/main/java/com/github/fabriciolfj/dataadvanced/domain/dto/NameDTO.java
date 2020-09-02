package com.github.fabriciolfj.dataadvanced.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface NameDTO {

    String getName();
}
