package com.sboard.controller.User;

import com.sboard.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

}
