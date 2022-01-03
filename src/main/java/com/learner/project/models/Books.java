package com.learner.project.models;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Ismansky Maxim
 * Date: 23.12.2021
 */
@Data
@Table(name="books")
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String author;

    private String pathName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
