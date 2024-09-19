package com.sboard.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping (value = "/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping (value = "/user/register")
    public String register(){
        return "/user/register";
    }

    @GetMapping (value = "/user/terms")
    public String terms(){
        return "/user/terms";
    }
}
