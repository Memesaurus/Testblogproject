package com.BlogSite.TestBlogProject.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("0")
@EqualsAndHashCode(callSuper = true)
public class Post extends BlogMessage {
    @OneToMany
    @JoinColumn(name = "parentid",
            foreignKey = @ForeignKey(name = "parent_id_fk"))
    private Set<Comment> comments;

    public Post() {
        super();
        this.comments = new HashSet<>();
    }

    public Post(Long id, String body, User user) {
        super(id, body, user);
        this.comments = new HashSet<>();
    }
}
