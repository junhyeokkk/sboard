package com.sboard.controller.Article;

import com.sboard.Service.ArticleService;
import com.sboard.Service.FileService;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class WriteController {

    private final ArticleService articleService;
    private final FileService fileService;

    // 글 추가 메서드
    @PostMapping("/article/write")
    public String write(ArticleDTO articleDTO, HttpServletRequest request) {
        articleDTO.setRegip(request.getRemoteAddr());
        log.info(articleDTO);

        // 파일 업로드
        List<FileDTO> uploadFiles = fileService.uploadFile(articleDTO);

        // 글 저장
        articleDTO.setFile(uploadFiles.size());
        log.info("업로드 파일 개수 : " + uploadFiles.size());
        int ano = articleService.insertArticle(articleDTO);

        // 파일 저장
        for(FileDTO fileDTO : uploadFiles) {
            fileDTO.setAno(ano);
            fileService.insertFile(fileDTO);
        }


        return "redirect:/article/list";
    }
}
