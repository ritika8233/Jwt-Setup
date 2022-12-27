package com.example.Blogger.controller;

import com.example.Blogger.common.ApiRestHandler;
import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.common.wrapper.RequestWrapper;
import com.example.Blogger.common.wrapper.ResponseWrapper;
import com.example.Blogger.model.Author;
import com.example.Blogger.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/author")
public class AuthorController extends ApiRestHandler {

    @Autowired
    private AuthorService authorService;


    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseWrapper allAuthors(HttpServletRequest request){
        return ResponseWrapper.successResponse(request.getRequestURI(), "Authors List Fetched", authorService.authorList());
    }

    @RequestMapping(value = "/findAuthor/{email}", method = RequestMethod.GET)
    public ResponseWrapper fetchAuthor(@PathVariable String email, HttpServletRequest request){
        return ResponseWrapper.successResponse(request.getRequestURI(), "Authors List Fetched", authorService.getAuthor(email));
    }

    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
    public ResponseWrapper saveAuthor(@RequestBody RequestWrapper<Author> requestWrapper, HttpServletRequest request){
        if(!authorService.saveAuthor(requestWrapper.getData())){
            throw new DataValidationException("Something went wrong!!");
        }
        return ResponseWrapper.successResponse(request.getRequestURI(), "Author saved successfully", requestWrapper.getData());
    }

    @RequestMapping(value = "/editAuthor", method = RequestMethod.PUT)
    public ResponseWrapper editAuthor(@RequestBody RequestWrapper<Author> requestWrapper, HttpServletRequest request){
        Author author = requestWrapper.getData();
        return ResponseWrapper.successResponse(request.getRequestURI(), "Author Edited", authorService.editAuthor(author));
    }

    @RequestMapping(value = "/deleteAuthor/{email}", method = RequestMethod.DELETE)
    public ResponseWrapper deleteAuthor(@PathVariable String email , HttpServletRequest request){
        authorService.deleteAuthor(email);
        return ResponseWrapper.successResponse(request.getRequestURI(), "Author Deleted", null);
    }

}
