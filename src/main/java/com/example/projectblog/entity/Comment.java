package com.example.projectblog.entity;

import com.example.projectblog.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 설정
    @JoinColumn
    private Post post;

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
