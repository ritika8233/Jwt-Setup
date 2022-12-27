package com.example.Blogger.service;

import com.example.Blogger.common.enums.Role_Enum;
import com.example.Blogger.common.exceptions.DataValidationException;
import com.example.Blogger.config.JwtTokenUtil;
import com.example.Blogger.dto.request.LoginRequest;
import com.example.Blogger.dto.request.RegisterationRequest;
import com.example.Blogger.dto.response.LoginResponse;
import com.example.Blogger.model.Author;
import com.example.Blogger.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GlobalService {

    @Autowired AuthorRepository authorRepository;

    @Autowired private PasswordEncoder bcryptEncoder;

    @Autowired JwtTokenUtil jwtTokenUtil;

    public LoginResponse loginResponse(LoginRequest loginRequest){
        Author author = authorRepository.findByUsername(loginRequest.getUsername());
        if(author == null){
            throw new DataValidationException("Author not found");
        }
        if(!bcryptEncoder.matches(loginRequest.getPassword(), author.getPassword())){
            throw new DataValidationException("Password does not matches");
        }
        Map<String, Object> claims = new HashMap<>();
        String token = jwtTokenUtil.doGenerateToken(claims, author.getUsername());
        return new LoginResponse(author, token);
    }

    public LoginResponse registerationRequest(RegisterationRequest registerationRequest){
        Author existingAuthor = authorRepository.findByUsername(registerationRequest.getUsername());
        if(existingAuthor != null){
            throw new DataValidationException("Author already exists");
        }
        Author author = new Author();
        author.setRole(Role_Enum.USER);
        author.setPassword(bcryptEncoder.encode(registerationRequest.getPassword()));
        author.setUsername(registerationRequest.getUsername());

        authorRepository.save(author);

        Map<String, Object> claims = new HashMap<>();
        String token = jwtTokenUtil.doGenerateToken(claims, author.getUsername());
        return new LoginResponse(author, token);
    }

}
