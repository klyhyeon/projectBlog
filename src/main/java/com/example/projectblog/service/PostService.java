package com.example.projectblog.service;

import com.example.projectblog.dto.PostRequestDto;
import com.example.projectblog.entity.Post;
import com.example.projectblog.jwt.JwtUtil;
import com.example.projectblog.repository.PostRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public Post createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        Post post = new Post(postRequestDto);
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token)) postRepository.save(post);
        return post;
    }
    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void update(Long id, HttpServletRequest request, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims userInfo = jwtUtil.getUserInfoFromToken(token);
        String username = userInfo.getSubject();
        String name = post.getUsername();

        if (jwtUtil.validateToken(token) && username.equals(name))
            post.update(postRequestDto);

//        if (comparePwd(id, postRequestDto)) {
//            post.update(postRequestDto);
//            return true;
//        } else {
//            return false;
//        }
    }

    public void delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token))
            postRepository.deleteById(id);
//        if (comparePwd(id, postRequestDto)) {
//            postRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
    }
//    public boolean comparePwd (Long id, PostRequestDto postRequestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        return post.getPassword().equals(postRequestDto.getPassword());
//    }
}
