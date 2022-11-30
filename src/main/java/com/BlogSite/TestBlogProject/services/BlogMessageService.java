package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.models.BlogMessage;
import com.BlogSite.TestBlogProject.models.Comment;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;

import java.util.List;

public interface BlogMessageService {
    Result<List<Post>> getActivePostsByUserUsername(String username);

    List<Post> getAllPosts();

    Result<Post> addPost(BlogMessageDto blogMessageDto);

    Result<Comment> addCommentToPost(BlogMessageDto blogMessageDto, Long parentId);

    void deleteAllPosts();

    void deleteBlogMessage(Long postId);

    Result<BlogMessage> hideUserBlogMessage(Long postId);

    Result<BlogMessage> changeUserBlogMessageLikeState(Long postId);

    Result<Long> getLikeCountByBlogMessageId(Long postId);
}
