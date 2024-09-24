package com.sboard.repository.custom;

import com.querydsl.core.Tuple;
import com.sboard.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    public Page<Tuple> selectArticleAllForList(PageRequestDTO requestDTO, Pageable pageable);
    public Page<Tuple> selectArticleForSearch(PageRequestDTO requestDTO, Pageable pageable);

}
