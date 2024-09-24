package com.sboard.entity;

import com.sboard.dto.ArticleDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    // 추가 필드
    @Transient // 엔티티에 속성에서 제외시키는 어노테이션, 테이블 컬럼 생성 안함
    private String nick;

    /*
        DTO 변환 메서드 대신 ModelMapper 사용
    */

}
