package com.sboard.Service;

import com.sboard.dto.CommentDTO;
import com.sboard.entity.Comment;
import com.sboard.entity.User;
import com.sboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentDTO insertComment(CommentDTO commentDTO) {

        Comment comment= modelMapper.map(commentDTO, Comment.class);
        User user = User.builder()
                .uid(commentDTO.getWriter())
                .nick(commentDTO.getUser().getNick())
                .build();
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDTO.class);
    }
    public CommentDTO selectComment(int no) {
        return null;
    }
    public List<CommentDTO> selectCommentAll() {
        return null;
    }
    public void updateComment(CommentDTO commentDTO) {
    }
    public void deleteComment(int no) {
    }
}
