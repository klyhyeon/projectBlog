package com.example.projectblog.dto;

import com.example.projectblog.entity.Comment;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long postId;

    private String username;

    private String comment;

    public CommentResponseDto(Long userId, Long postId, Comment comment) {
        this.userId = userId;
        this.postId = postId;
        this.username = comment.getUsername();
        this.comment = comment.getComment();
    }
}
