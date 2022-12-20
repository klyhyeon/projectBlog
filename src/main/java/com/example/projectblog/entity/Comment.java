package com.example.projectblog.entity;

import com.example.projectblog.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private String username;

    @Column
    private String comments;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getComments() {
        return comments;
    }

    public Comment(CommentRequestDto commentRequestDto, Long userId) {
        this.username = commentRequestDto.getUsername();
        this.comments = commentRequestDto.getComments();
        this.userId = userId;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.username = commentRequestDto.getUsername();
        this.comments = commentRequestDto.getComments();
    }
}
