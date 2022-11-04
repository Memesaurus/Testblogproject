package com.BlogSite.TestBlogProject.mapper;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post postDtoToPost(PostDto postDto);
}
