package com.example.projectblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Setter
    private Post post;

    @Column
    private String username;

    @Column
    private String comment;

    //    @Builder
    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

//    public static Comment createComment(String username, String comment, Post post) {
//        return Comment.builder()
//                .username(username)
//                .comment(comment)
//                .post(post)
//                .build();
//    }

//    public void update(CommentRequestDto commentRequestDto) {
//        this.username = commentRequestDto.getUsername();
//        this.comment = commentRequestDto.getComment();
//    }
}
