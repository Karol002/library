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
        name = "Reader.getAllReaders",
        query = "SELECT * FROM readers",
        resultClass = Reader.class
)

@NamedNativeQuery(
        name = "Reader.getReader",
        query = "SELECT * FROM readers WHERE ID = :id",
        resultClass = Reader.class
)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "READERS")
public class Reader {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name="ID", unique=true)
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "SIGN_UP_DATE")
    private LocalDate signUpDate;

    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "reader",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Borrow> borrows = new ArrayList<>();

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.signUpDate = LocalDate.now();
    }

    public Reader(Long id, String firstName, String lastName, LocalDate signUpDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.signUpDate = signUpDate;
    }
}