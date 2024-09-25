package com.sboard.Service;

import com.querydsl.core.Tuple;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import com.sboard.dto.PageRequestDTO;
import com.sboard.dto.PageResponseDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Optional<Article> opt = articleRepository.findById(no);
        if(opt.isPresent()) {
            Article article = opt.get();
            log.info(article);
            ArticleDTO dto = modelMapper.map(article, ArticleDTO.class);
            return dto;
        }
        return null;
    }

    public PageResponseDTO selectArticleAll(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Tuple> pageArticle = null;

        // 엔티티 조회
        // List<Article> articles = articleRepository.findAll();
        if(pageRequestDTO.getKeyword() == null) {
            // 일반 글목록 조회
            pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable);
        } else {
            // 검색 글목록 조회
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable);
        }

        // 엔티티 리스트를 DTO로 변환
        List<ArticleDTO> articleDTOs = pageArticle.getContent().stream().map(tuple -> {
               Article article = tuple.get(0, Article.class );
               String nick = tuple.get(1, String.class );
               article.setNick(nick);

               return modelMapper.map(article, ArticleDTO.class);

        }).toList();

        int total  = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleDTOs)
                .total(total)
                .build();
    }
    public void updateArticle(ArticleDTO articleDTO) {

    }

    public void deleteArticle(int no) {

    }
}
