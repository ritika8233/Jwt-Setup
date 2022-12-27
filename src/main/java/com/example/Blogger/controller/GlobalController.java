package com.example.Blogger.controller;

import com.example.Blogger.common.wrapper.ResponseWrapper;
import com.example.Blogger.dto.request.LoginRequest;
import com.example.Blogger.dto.request.RegisterationRequest;
import com.example.Blogger.dto.response.LoginResponse;
import com.example.Blogger.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/global")
public class GlobalController {

    @Autowired GlobalService globalService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseWrapper login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        LoginResponse loginResponse = globalService.loginResponse(loginRequest);
        return ResponseWrapper.successResponse(request.getRequestURI(), "Token generated", loginResponse);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseWrapper register(@RequestBody RegisterationRequest registerationRequest, HttpServletRequest request){
        LoginResponse loginResponse = globalService.registerationRequest(registerationRequest);
        return ResponseWrapper.successResponse(request.getRequestURI(), "Token generated", loginResponse);
    }
}
