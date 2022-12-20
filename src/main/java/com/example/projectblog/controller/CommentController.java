package com.example.projectblog.controller;

import com.example.projectblog.dto.CommentRequestDto;
import com.example.projectblog.dto.CommentResponseDto;
import com.example.projectblog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }

    @PutMapping("/api/posts/{id}/{commentId}")
    public void updateComment(@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request, CommentRequestDto commentRequestDto) {
        commentService.update(id,commentId,request,commentRequestDto);
    }

    @DeleteMapping("/api/posts/{id}/{commentId}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId,HttpServletRequest request) {
        return commentService.delete(id, commentId, request);
    }
}
