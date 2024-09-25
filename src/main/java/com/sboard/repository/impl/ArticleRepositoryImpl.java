package com.sboard.repository.impl;

import com.querydsl.core.Query;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sboard.dto.PageRequestDTO;
import com.sboard.entity.QArticle;
import com.sboard.entity.QFileEntity;
import com.sboard.entity.QUser;
import com.sboard.repository.ArticleRepository;
import com.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QArticle qArticle =  QArticle.article;
    private QUser qUser =  QUser.user;
    private QFileEntity qFileEntity =  QFileEntity.fileEntity;

    @Override
    public Page<Tuple> selectArticleAllForList(PageRequestDTO requestDTO, Pageable pageable) {

        List<Tuple> content = queryFactory.select(qArticle, qUser.nick)
                                                    .from(qArticle)
                                                    .join(qUser)
                                                    .on(qArticle.writer.eq(qUser.uid))
                                                    .offset(pageable.getOffset())
                                                    .limit(pageable.getPageSize())
                                                    .orderBy(qArticle.no.desc())
                                                    .fetch();
        log.info("content : " +content);

        long total = queryFactory
                    .select(qArticle.count())
                    .from(qArticle)
                    .fetchOne();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Tuple> selectArticleForSearch(PageRequestDTO requestDTO, Pageable pageable) {

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        // 검색 선택 조건에 따라 where 조건 표현식 생성
        BooleanExpression expression = null;

        if(type.equals("title")){
            expression = qArticle.title.contains(keyword);
            log.info(expression);
        }else if(type.equals("content")){
            expression = qArticle.content.contains(keyword);
            log.info(expression);
        }else if(type.equals("title_content")){
            BooleanExpression titleExpression = qArticle.title.contains(keyword);
            BooleanExpression  contentExpression = qArticle.content.contains(keyword);

            expression = titleExpression.or(contentExpression);
            log.info(expression);
        }else if(type.equals("writer")){
            expression = qUser.nick.contains(keyword);
            log.info(expression);
        }

        List<Tuple> content = queryFactory.select(qArticle, qUser.nick)
                                        .from(qArticle)
                                        .where(expression)
                                        .join(qUser)
                                        .on(qArticle.writer.eq(qUser.uid))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .orderBy(qArticle.no.desc())
                                        .fetch();
        log.info("content : " +content);

        long total = queryFactory
                .select(qArticle.count())
                .from(qArticle)
                .where(expression)
                .join(qUser)
                .on(qArticle.writer.eq(qUser.uid))
                .fetchOne();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }

}
