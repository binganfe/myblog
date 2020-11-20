package com.bingan.myblog.service;

import com.bingan.myblog.entity.Blog;
import com.bingan.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page listBlogs(Pageable pageable, BlogQuery blog);

    Page listBlogs(Pageable pageable, Long tagId);

    Page listBlogs(Pageable pageable);

    Page listBlogs(Pageable pageable, String query);

    List<Blog> listRecommendBlogsTop(int size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void removeBlog(Long id);
}
