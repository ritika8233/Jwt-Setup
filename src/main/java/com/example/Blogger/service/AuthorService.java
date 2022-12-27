package com.example.Blogger.service;

import com.example.Blogger.common.enums.Role_Enum;
import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.repository.AuthorRepository;
import com.example.Blogger.model.Author;
import com.example.Blogger.repository.BlogRepository;
import com.smattme.requestvalidator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorService {

    @Autowired private AuthorRepository authorRepository;
    @Autowired private BlogRepository blogRepository;
    @Autowired private PasswordEncoder bcryptEncoder;

    public Boolean saveAuthor(Author author){
        /*Map<String, String> rules = new HashMap<>();
        rules.put("email", "required|email");

        List<String> validate = RequestValidator.validate(author, rules);

        if(!validate.isEmpty()){
            System.out.println("Error");
            throw new DataValidationException("Email is Invalid");
        }*/

        if(authorRepository.findByUsername(author.getUsername()) != null){
            throw new DataValidationException("Email already exists");
        }
        author.setRole(Role_Enum.USER);
        author.setPassword(bcryptEncoder.encode(author.getPassword()));
        authorRepository.save(author);
        return true;
    }

    public Author editAuthor(Author newAuthor){
        Author prevAuthor = authorRepository.getById(newAuthor.getId());
        if(prevAuthor == null){
            throw new DataValidationException("Author does not exist");
        }
        if(newAuthor.getUsername() != null){
            prevAuthor.setUsername(newAuthor.getUsername());
        }
        authorRepository.save(prevAuthor);
        return prevAuthor;
    }

    @Transactional
    public Boolean deleteAuthor(String email){
        Author author = getAuthor(email);
        blogRepository.removeByAuthor(author);

        return true;
    }

    public List<Author> authorList(){
        return authorRepository.findAll();
    }

    public Author getAuthor(String username){
        if(username==null ){
            throw new DataValidationException("Username is null");
        }
        Author author = authorRepository.findByUsername(username);
        if(author == null){
            throw new DataValidationException("Author does not exist");
        }
        return author;
    }

}
