package com.github.fabriciolfj.dataadvanced.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;


import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Author.fetchName",
        query = "SELECT a.name From Author a"
)
@NamedQuery(
        name = "Author.fetchNameAndAge2",
        query = "SELECT a.age as age, a.name As name From Author a"
)
@NamedNativeQuery(
        name = "Author.fetchName2",
        query = "Select name from author"
)
@NamedNativeQuery(
        name = "Author.fetchNameAndAge",
        query = "SELECT a.age, a.name From author a"
)
@NamedEntityGraph(
        name = "author-books-publisher-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "books", subgraph = "publisher-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "publisher-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("publisher")
                        })
        }
)
public class Author implements Serializable {

    private static final long serialVersionUID = 4225172803841161426L;

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ToString.Include
    private String name;
    @ToString.Include
    @Basic(fetch = FetchType.LAZY)
    private String genre;
    @Basic(fetch = FetchType.LAZY)
    @ToString.Include
    private int age;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true)
    @BatchSize(size = 3)
    private List<Book> books = new ArrayList<>();
    @Version
    private short version;

    public void addBook(final Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBooks() {
        books.clear();
    }

    public void removeBook(final Book book) {
        book.setAuthor(null);
        this.books.remove(book);
    }
}
