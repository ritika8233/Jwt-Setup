package com.example.Blogger.dto.response;

import com.example.Blogger.model.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Author author;
    private String token;
}
