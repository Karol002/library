package com.library.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "copies")
public class Copy {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name="ID", unique=true)
    private Long id;
    @Column(name = "STATUS")
    private String status;
    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private Title title;
    @OneToOne
    @JoinColumn(name = "BORROW_ID")
    @Transient
    private BorrowedBook borrowedBook;
}