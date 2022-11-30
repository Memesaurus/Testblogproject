package com.BlogSite.TestBlogProject.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("1")
@EqualsAndHashCode(callSuper = true)
public class Comment extends BlogMessage {
    @Column(name = "parentid")
    private Long parentId;

    public Comment() {
        super();
    }

    public Comment(Long id, String body, User user, Long parentId) {
        super(id, body, user);
        this.parentId = parentId;
    }
}
