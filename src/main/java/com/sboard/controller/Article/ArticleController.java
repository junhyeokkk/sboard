package com.sboard.controller.Article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    @GetMapping(value = "/article/list")
    public String list() {
        return "/article/list";
    }

    @GetMapping(value = "/article/modify")
    public String modify() {
        return "/article/modify";
    }

    @GetMapping(value = "/article/view")
    public String view() {
        return "/article/view";
    }

    @GetMapping(value = "/article/write")
    public String write() {
        return "/article/write";
    }
}
