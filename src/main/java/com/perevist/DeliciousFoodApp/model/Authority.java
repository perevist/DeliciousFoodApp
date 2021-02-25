package com.perevist.DeliciousFoodApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    public enum AuthorityName {
        ROLE_USER,
        ROLE_ADMIN
    }
}
