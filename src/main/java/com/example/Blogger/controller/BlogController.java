package com.example.Blogger.controller;

import com.example.Blogger.common.ApiRestHandler;
import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.common.wrapper.RequestWrapper;
import com.example.Blogger.common.wrapper.ResponseWrapper;
import com.example.Blogger.model.Blog;
import com.example.Blogger.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/blog")
//@ControllerAdvice(annotations = RestController.class)
public class BlogController extends ApiRestHandler {

    @Autowired
    private BlogService blogService;


    @RequestMapping(value = "/findBlog/{blog_id}", method = RequestMethod.GET)
    public ResponseWrapper<Blog> fetchBlog(@PathVariable Long blog_id, HttpServletRequest request){
        return ResponseWrapper.successResponse(request.getRequestURI(), "Blog Fetched", blogService.findBlog(blog_id));
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseWrapper<Blog> allBlogs(HttpServletRequest request){
        return ResponseWrapper.successResponse(request.getRequestURI(), "Blogs List Fetched", blogService.allBlogs());
    }

    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    public ResponseWrapper saveBlog(@RequestBody RequestWrapper<Blog> requestWrapper, HttpServletRequest request){
        Map<String, List<Blog>> map = new HashMap<>();
        Blog blog = requestWrapper.getData();

        if(!blogService.saveBlog(blog)){
            throw new DataValidationException("Something went wrong!! Try Again");
        }
        map.put("Blog Details", new ArrayList<>(Collections.singleton(blog)));
        return  ResponseWrapper.successResponse(request.getRequestURI(), "Blog saved successfully", blog);
    }


    @RequestMapping(value = "/editBlog", method = RequestMethod.PUT)
    public ResponseWrapper editBlog(@RequestBody RequestWrapper<Blog> requestWrapper, HttpServletRequest request){
        Blog newBlog = requestWrapper.getData();
        Blog oldBlog = blogService.findBlog(newBlog.getId());

        return ResponseWrapper.successResponse(request.getRequestURI(), "Blog Edited", blogService.editBlog(newBlog, oldBlog));
    }


    @RequestMapping(value = "/deleteBlog/{blog_id}", method = RequestMethod.DELETE)
    public ResponseWrapper deleteBlog(@PathVariable Long blog_id, HttpServletRequest request){
        blogService.deleteBlog(blog_id);
        return ResponseWrapper.successResponse(request.getRequestURI(), "Blog Deleted", null);
    }

}
