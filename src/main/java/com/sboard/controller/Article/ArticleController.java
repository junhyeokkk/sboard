package com.sboard.controller.Article;

import com.sboard.Service.ArticleService;
import com.sboard.config.AppInfo;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.PageRequestDTO;
import com.sboard.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/article/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {


            PageResponseDTO pageResponseDTO = articleService.selectArticleAll(pageRequestDTO);
            model.addAttribute("pageResponseDTO", pageResponseDTO);

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
