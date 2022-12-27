package com.example.Blogger.service;

import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.repository.AuthorRepository;
import com.example.Blogger.repository.BlogRepository;
import com.example.Blogger.model.Author;
import com.example.Blogger.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired private BlogRepository blogRepository;

    @Autowired private AuthorService authorService;
    @Autowired private AuthorRepository authorRepository;

    public Blog findBlog(Long blog_id){
        Blog blog = blogRepository.findById(blog_id).orElse(null);
        if(blog == null){
            throw new DataValidationException("Blog not found");
        }
        return blog;
    }

    public List<Blog> allBlogs(){
        return blogRepository.findAll();
    }

    public Boolean saveBlog(Blog blog){
        String username = blog.getAuthor().getUsername();
//        System.out.println(email);

        Author author = authorService.getAuthor(username);
        blog.setAuthor(author);
        blogRepository.save(blog);
        return Boolean.TRUE;
    }

    public Blog editBlog(Blog newBlog, Blog oldBlog){
        if(newBlog.getAuthor() != null){
            oldBlog.setAuthor(newBlog.getAuthor());
        }
        if(newBlog.getCategory() != null){
            oldBlog.setCategory(newBlog.getCategory());
        }
        if(newBlog.getTitle() != null){
            oldBlog.setTitle(newBlog.getTitle());
        }
        if(newBlog.getDescription() != null){
            oldBlog.setDescription(newBlog.getDescription());
        }
        blogRepository.save(oldBlog);
        return oldBlog;
    }

    public Boolean deleteBlog(Long id){
        Blog blog = findBlog(id);
        blogRepository.deleteById(id);
        return true;
    }
}
