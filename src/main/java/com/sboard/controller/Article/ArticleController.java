package com.sboard.controller.Article;

import com.sboard.config.AppInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ArticleController {


    @GetMapping("/article/list")
    public String list(Model model) {

        return "/article/list";
    }

    @GetMapping("/article/write")
    public String write(Model model) {

        return "/article/write";
    }

    @GetMapping("/article/view")
    public String view(Model model) {

        return "/article/view";
    }
}
