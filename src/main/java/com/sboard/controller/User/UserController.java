package com.sboard.controller.User;

import com.sboard.config.AppInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class UserController {


    private final AppInfo appInfo;

    @GetMapping (value = "/user/login")
    public String login(Model model){
        model.addAttribute(appInfo);
        return "/user/login";
    }

    @GetMapping (value = "/user/register")
    public String register(){
        return "/user/register";
    }

}
