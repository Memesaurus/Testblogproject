package com.BlogSite.TestBlogProject.mapper;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Post postDtoToPost(PostDto postDto, User user);
}
