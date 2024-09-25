package com.sboard.dto;

import com.sboard.entity.Article;
import com.sboard.entity.Comment;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {

    private int no;

    @Builder.Default
    private String cate = "free";
    private String title;
    private String content;
    private int comment;

    private List<MultipartFile> files;

    @Builder.Default
    private int file = 0;

    @Builder.Default
    private int hit = 0;

    private String writer;
    private String regip;
    private String rdate;

    // 추가 필드
    private String nick;
    private List<FileDTO> fileList;
    private List<Comment> commentList;

    /*
        Entity 변환 메서드 대신 ModelMapper 사용
    */

}
