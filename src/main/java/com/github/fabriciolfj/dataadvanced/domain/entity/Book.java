package com.github.fabriciolfj.dataadvanced.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "book")
@Setter
@BatchSize(size = 3)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 548296319265961874L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;
    @ToString.Include
    private String title;
    @ToString.Include
    private String isbn;
    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns
   ({@JoinColumn(name = "name", referencedColumnName = "name"), @JoinColumn(name = "age", referencedColumnName = "age")})
    @JsonIgnore
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @ToString.Include
    private BigDecimal price;
    @Version
    private short version;
}
