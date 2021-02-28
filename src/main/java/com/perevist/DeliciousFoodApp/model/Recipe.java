package com.perevist.DeliciousFoodApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private RecipeCategory recipeCategory;
    @OneToMany
    @JoinColumn(name = "recipeId")
    private List<Comment> comments;
    private String title;
    @Column(name = "image_url")
    private String imageUrl;
    private String content;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
