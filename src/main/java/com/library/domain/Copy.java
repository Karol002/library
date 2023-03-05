package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NamedNativeQuery(
        name = "Copy.getAllAvailableCopies",
        query = "SELECT * FROM copies WHERE TITLE_ID = :titleId AND IS_BORROWED = false",
        resultClass = Copy.class
)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COPIES")
public class Copy {
    @Id
    @NotNull
    @GeneratedValue
    @Column(name="ID", unique=true)
    private Long id;

    @NotNull
    @Column(name = "IS_BORROWED")
    private boolean isBorrowed;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private Title title;

    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "copy",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private List<Borrow> borrows = new ArrayList<>();

    public Copy(Title title) {
        this.title = title;
    }

    public Copy(Long id, boolean isBorrowed, Title title) {
        this.id = id;
        this.isBorrowed = isBorrowed;
        this.title = title;
    }
}