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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.annotation.Transient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Table(name = "book")
@Setter
//@BatchSize(size = 3)
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
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @ToString.Include
    private Double price;
    @Version
    private short version;
    @Generated(value = GenerationTime.ALWAYS)
    @Column(insertable = false, updatable = false, columnDefinition = "DOUBLE AS (price - price * 0.25)")
    private Double desconto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula(("(SELECT b.id FROM book b WHERE b.price < price AND b.author_id = author_id ORDER BY b.price DESC LIMIT 1)"))
    private Book nextBook;

    /*@PostLoad
    private void aplicaDesconto() {
        if(this.price == null) {
            this.desconto = BigDecimal.ZERO;
            return;
        }

        this.desconto = this.price.subtract(this.price.multiply(new BigDecimal(0.2))).setScale(2, RoundingMode.HALF_EVEN);
    }*/
}
