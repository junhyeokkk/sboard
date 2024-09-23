package com.sboard.Service;

import com.sboard.dto.ArticleDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public int insertArticle(ArticleDTO articleDTO) {


        // ModelMapper를 이용한 DTO를 Entity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article);

        // 저장 (save 메서드는 insert 후 select 진행)
        Article savedArticle = articleRepository.save(article);

        // 저장된 글번호 리턴
        return savedArticle.getNo();
    }

    public ArticleDTO selectArticle(int no) {

        return null;
    }

    public List<ArticleDTO> selectArticleAll() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleDTO> articleDTOs = new ArrayList<>();
   //     articleDTOs = articles.stream().map(entity -> modelMapper.map(Article, ArticleDTO.class ))
        return null;
    }
    public void updateArticle(ArticleDTO articleDTO) {

    }

    public void deleteArticle(int no) {

    }
}
