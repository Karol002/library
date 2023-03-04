package com.library.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Column(name = "STATUS")
    private String status;

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

    public Copy(String status, Title title) {
        this.status = status;
        this.title = title;
    }

    public Copy(Long id, String status, Title title) {
        this.id = id;
        this.status = status;
        this.title = title;
    }
}