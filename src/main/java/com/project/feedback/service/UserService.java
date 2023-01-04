package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.exception.CustomException;
import com.project.feedback.auth.JwtTokenUtil;
import com.project.feedback.repository.UserRepository;
import com.project.feedback.domain.dto.user.UserChangeRoleRequest;
import com.project.feedback.domain.dto.user.UserChangeRoleResponse;
import com.project.feedback.domain.dto.user.UserJoinRequest;
import com.project.feedback.domain.dto.user.UserJoinResponse;
import com.project.feedback.domain.dto.user.UserListDto;
import com.project.feedback.domain.dto.user.UserListResponse;
import com.project.feedback.domain.dto.user.UserLoginRequest;
import com.project.feedback.domain.dto.user.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final FindService findService;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMs = 1000 * 60 * 60;

    public UserJoinResponse saveUser(UserJoinRequest req) {

        // userName 중복 체크
        userRepository.findByUserName(req.getUserName()).ifPresent(user -> {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME, "UserName(" + req.getUserName() + ")이 중복입니다.");
        });

        // 비밀번호 인코딩 후 저장
        String encodedPassword = encoder.encode( req.getPassword() );
        User savedUser = userRepository.save(req.toEntity(encodedPassword));

        return UserJoinResponse.of(savedUser);
    }

    public UserLoginResponse login(UserLoginRequest req) {

        User user = findService.findUserByUserName(req.getUserName());

        // 비밀번호 체크
        if(!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // JWT Token 발급
        String jwtToken = JwtTokenUtil.createToken(user.getUserName(), secretKey, expireTimeMs);
        return new UserLoginResponse(jwtToken);
    }

    public UserChangeRoleResponse changeRole(Long userId, UserChangeRoleRequest req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));

        Role newRole;

        // "admin" 또는 "user"을 입력받는다면 입력받은 값으로 Role 수정
        if(req.getRole().toLowerCase().equals("admin")) {
            newRole = Role.ADMIN;
        } else if(req.getRole().toLowerCase().equals("user")) {
            newRole = Role.USER;
        } else {
            throw new RuntimeException();
        }

        user.setRole(newRole);
        userRepository.save(user);

        return UserChangeRoleResponse.of(user.getId(), newRole);
    }

    public UserListResponse getUserList(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        List<UserListDto> content = new ArrayList<>();
        for(User user : users) {
            content.add(UserListDto.of(user));
        }

        return new UserListResponse(content, pageable, users);
    }
}
