package com.example.Blogger.model;

import com.example.Blogger.common.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_email")
    private Author author;

    @Enumerated(EnumType.STRING)
    private Categories category;
}
