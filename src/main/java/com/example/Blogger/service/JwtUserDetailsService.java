package com.example.Blogger.service;

import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.model.Author;
import com.example.Blogger.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorRepository.findByUsername(username);
        if(author == null){
            throw new DataValidationException("User Not Found");
        }
        return new User(author.getUsername(), author.getPassword(), new ArrayList<>());
    }
}
