package com.sboard.controller.Comment;

import com.sboard.Service.CommentService;
import com.sboard.dto.CommentDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public ResponseEntity<CommentDTO> write(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {

        System.out.println("asdfffffffffffffffff");
        String regip = request.getRemoteAddr();
        commentDTO.setRegip(regip);

        log.info(commentDTO);

        CommentDTO dto = commentService.insertComment(commentDTO);

        return ResponseEntity
                .ok()
                .body(dto);
    }

}
