package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQuery(
        name = "Borrow.getAllBorrows",
        query = "SELECT b FROM Borrow b JOIN FETCH b.copy JOIN FETCH b.reader"
)

@NamedQuery(
        name = "Borrow.getBorrow",
        query = "SELECT b FROM Borrow b JOIN FETCH b.copy JOIN FETCH b.reader WHERE b.id = :id"
)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BORROWS")
public class Borrow {
    @Id
    @NotNull
    @GeneratedValue
    @Column(name="ID", unique=true)
    private Long id;

    @Column(name = "BORROW_DATE")
    private LocalDate borrowDate;

    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "COPY_ID")
    private Copy copy;

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    public Borrow(LocalDate borrowDate, LocalDate returnDate, Copy copy, Reader reader) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.copy = copy;
        this.reader = reader;
    }

    public Borrow(Copy copy, Reader reader) {
        borrowDate = LocalDate.now();
        this.copy = copy;
        this.reader = reader;
    }
}