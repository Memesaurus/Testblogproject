package com.BlogSite.TestBlogProject.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN parentid is not null THEN 1 ELSE 0 END")
@Getter
@Setter
@EqualsAndHashCode
public class BlogMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isactive")
    private Boolean isActive;
    private String body;
    @Column(name = "likecount")
    private Long likeCount;
    @ManyToMany
    @JoinTable(name = "posts_users_likes",
            joinColumns = @JoinColumn(name = "postid"),
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
    @Fetch(FetchMode.JOIN)
    private Set<User> userLikes;
    @ManyToOne
    @JoinColumn(name = "userid",
            foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    public BlogMessage() {
        this.userLikes = new HashSet<>();
        this.likeCount = 0L;
        this.isActive = true;
    }

    public BlogMessage(Long id, String body, User user) {
        this();
        this.id = id;
        this.body = body;
        this.user = user;
    }

    public synchronized void addLike(User user) {
        this.userLikes.add(user);
        this.likeCount++;
    }

    public synchronized void removeLike(User user) {
        this.userLikes.remove(user);
        this.likeCount--;
    }
}
