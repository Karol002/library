package com.library.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "borrows")
public class BorrowedBook {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="ID", unique=true)
    private Long id;
    @OneToOne
    @JoinColumn(name = "COPY_ID")
    private Copy copy;
    @ManyToOne
    @JoinColumn(name = "READER_ID")
    private Reader reader;
    @JoinColumn(name = "BORROW_DATE")
    private LocalDate borrowDate;
    @JoinColumn(name = "RETURN_ID")
    private LocalDate returnDate;
}