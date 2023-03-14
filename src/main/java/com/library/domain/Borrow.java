package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NamedNativeQuery(
        name = "Borrow.getAllBorrows",
        query = "SELECT * FROM borrows b JOIN copies c ON b.COPY_ID = c.ID JOIN readers r ON b.READER_ID = r.ID",
        resultClass = Borrow.class
)

@NamedNativeQuery(
        name = "Borrow.getBorrow",
        query = "SELECT * FROM borrows b JOIN copies c ON b.COPY_ID = c.ID JOIN readers r ON b.READER_ID = r.ID WHERE b.ID = :id",
        resultClass = Borrow.class
)

@NamedNativeQuery(
        name = "Borrow.getAllBorrowsByReaderId",
        query = "SELECT * FROM borrows WHERE READER_ID = :readerId",
        resultClass = Borrow.class
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

    @NotNull
    @Column(name = "BORROW_DATE")
    private LocalDate borrowDate;

    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;

    @NotNull
    @Column(name = "CLOSED")
    private boolean closed;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_ID")
    private Copy copy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    public Borrow(LocalDate borrowDate, LocalDate returnDate, boolean closed, Copy copy, Reader reader) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.closed = closed;
        this.copy = copy;
        this.reader = reader;
    }

    public Borrow(Copy copy, Reader reader) {
        borrowDate = LocalDate.now();
        closed = false;
        this.copy = copy;
        this.reader = reader;
    }
}