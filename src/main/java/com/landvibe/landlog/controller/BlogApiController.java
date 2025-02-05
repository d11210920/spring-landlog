package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.form.UpdateBlogForm;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/blogs")
public class BlogApiController {
    private final MemberService memberService;
    private final BlogService blogService;

    public BlogApiController(MemberService memberService, BlogService blogService) {
        this.memberService = memberService;
        this.blogService = blogService;
    }

    @GetMapping
    public List<Blog> list(@RequestParam long creatorId) {
        List<Blog> blogs = blogService.findBlogs(creatorId);
        return blogs;
    }


    @GetMapping("/{blogId}")
    public UpdateBlogForm blogUpdate(@RequestParam long creatorId, @PathVariable long blogId) {
        Blog blog = blogService.findOne(blogId, creatorId);
        UpdateBlogForm updateBlogForm = UpdateBlogForm.builder()
                .title(blog.getTitle())
                .contents(blog.getContents())
                .build();
        return updateBlogForm;
    }


    @PostMapping
    public Blog create(@RequestParam long creatorId, @RequestBody Blog blog) {
        blogService.create(blog);
        return blog;
    }


    @PutMapping("/{blogId}")
    public Blog update(@RequestParam long creatorId, @PathVariable long blogId, @RequestBody Blog blog) {
        UpdateBlogForm updateBlogForm = UpdateBlogForm.builder()
                .title(blog.getTitle())
                .contents(blog.getContents())
                .build();
        blogService.update(blogId, creatorId, updateBlogForm);

        return blog;

    }

    @DeleteMapping("/{id}")
    public void delete(@RequestParam long creatorId, @PathVariable long id) {
        blogService.delete(id, creatorId);
    }
}
