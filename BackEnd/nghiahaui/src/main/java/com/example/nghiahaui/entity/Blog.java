package com.example.nghiahaui.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="blog")
public class Blog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;


    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    private Timestamp createAt;

    @ManyToOne
    @JoinColumn(name="image_id")
    private Image image;


    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="blog_tag",joinColumns = @JoinColumn(name="blog_id"),inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tag> tags = new HashSet<>();

}
