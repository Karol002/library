package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "Title.getAllTitles",
        query = "SELECT * FROM titles",
        resultClass = Title.class
)

@NamedNativeQuery(
        name = "Title.getTitle",
        query = "SELECT * FROM titles WHERE ID = :id",
        resultClass = Title.class
)


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TITLES")
public class Title {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name="ID", unique=true)
    private Long id;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "AUTHOR")
    private String author;

    @NotNull
    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "title",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Copy> copies = new ArrayList<>();

    public Title(String title, String author, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Title(Long id, String title, String author, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }
}