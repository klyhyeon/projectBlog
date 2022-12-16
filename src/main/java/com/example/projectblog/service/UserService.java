package com.example.projectblog.service;

import com.example.projectblog.dto.LoginRequestDto;
import com.example.projectblog.dto.SignupRequestDto;
import com.example.projectblog.entity.User;
import com.example.projectblog.jwt.JwtUtil;
import com.example.projectblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 등록 시 제한사항 설정 및 확인
        String usernamePattern = "^[a-z0-9]{4,10}$";
        String pwdPattern = "^[a-zA-Z0-9]{8,15}$";
        if (!Pattern.matches(usernamePattern, username)) {
            throw new IllegalArgumentException("아이디를 다시 확인해주세요.");
        }
        if (!Pattern.matches(pwdPattern, password)) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");
        }

        User user = new User(username, password, email);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}