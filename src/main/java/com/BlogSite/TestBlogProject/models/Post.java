package com.BlogSite.TestBlogProject.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    @ManyToOne()
    @JoinColumn(name = "userid",
            foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;
}
