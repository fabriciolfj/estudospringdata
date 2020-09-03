package com.github.fabriciolfj.dataadvanced.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorId implements Serializable {

    private static final long serialVersionUID = 2812555571966679271L;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
}
