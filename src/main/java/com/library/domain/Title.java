package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "titles")
public class Title {
    @Id
    @GeneratedValue
    @Column(name="ID", unique=true)
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "PUBLICATION_DATE")
    private LocalDate publicationDate;
    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "title",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies;

    public Title(Long id, String title, String author, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }
}