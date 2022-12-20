package com.example.projectblog.entity;

import com.example.projectblog.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    @Column
    private String title;

    @Column
    private String username;

    @Column
    private String contents;

    @OneToMany(mappedBy = "post")// 연관관계 설정, 비어있는 코멘트 리스트 생성
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, Long userId, List<Comment> commentList) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.contents = postRequestDto.getContents();
        this.userId = userId;
        this.commentList = commentList;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.username = postRequestDto.getUsername();
        this.contents = postRequestDto.getContents();
    }
}
