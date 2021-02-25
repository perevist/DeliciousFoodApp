package com.perevist.DeliciousFoodApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User author;
    private Long recipeId;
    private String content;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
