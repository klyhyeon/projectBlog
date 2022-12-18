package com.example.projectblog.service;

import com.example.projectblog.dto.PostRequestDto;
import com.example.projectblog.dto.PostResponseDto;
import com.example.projectblog.entity.Post;
import com.example.projectblog.entity.User;
import com.example.projectblog.jwt.JwtUtil;
import com.example.projectblog.repository.PostRepository;
import com.example.projectblog.repository.UserRepository;
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

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 올바른 토큰인지 확인
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user.getId()));

            return new PostResponseDto(post);
        } else {
            return null;
        }
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
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );

            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 글입니다.")
            );

            post.update(postRequestDto, user.getId());
        }
    }

    public void delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token))
            postRepository.deleteById(id);
    }
}
