package com.project.feedback.service;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.dto.user.*;
import com.project.feedback.domain.entity.CourseEntity;
import com.project.feedback.domain.entity.CourseEntityUser;
import com.project.feedback.domain.entity.User;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.exception.CustomException;
import com.project.feedback.auth.JwtTokenUtil;
import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.CourseUserRepository;
import com.project.feedback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CourseUserRepository courseUserRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder encoder;
    private final FindService findService;

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${dummy.default-password}")
    private String defaultPw;
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

        // "admin" 또는 "student"을 입력받는다면 입력받은 값으로 Role 수정
        if(req.getRole().toLowerCase().equals("admin")) {
            newRole = Role.ROLE_ADMIN;
        } else if(req.getRole().toLowerCase().equals("student")) {
            newRole = Role.ROLE_STUDENT;
        } else {
            throw new RuntimeException();
        }

        user.setRole(newRole);
        userRepository.save(user);

        return UserChangeRoleResponse.of(user.getId(), newRole);
    }
    public void setDefaultUsers(){
        String[] defaultUserName = new String [4];
        defaultUserName[0] = "admin";
        defaultUserName[1] = "student";
        defaultUserName[2] = "manager";
        defaultUserName[3] = "teacher";
        for(int i = 0; i < defaultUserName.length; i++){
            try{
                findService.findUserByUserName(defaultUserName[i]);
            }catch (CustomException e){
                if(e.getErrorCode() == ErrorCode.USERNAME_NOT_FOUND){
                    UserJoinRequest req = new UserJoinRequest();
                    req.setUserName(defaultUserName[i]);
                    req.setPassword(defaultPw);
                    saveUser(req);
                    if(!defaultUserName[i].equals("student")) changeDefaultRole(defaultUserName[i]);
                }
            }

        }

    }
    public void changeDefaultRole(String userRole){
        User user = userRepository.findByUserName(userRole)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));
        Role newRole;
        if(userRole.equals("admin")){
            newRole = Role.ROLE_ADMIN;
        } else if (userRole.equals("manager")) {
            newRole = Role.ROLE_MANAGER;
        } else if (userRole.equals("teacher")) {
            newRole = Role.ROLE_TEACHER;
        } else{
            throw new RuntimeException();
        }
        user.setRole(newRole);
        userRepository.save(user);
    }
    public UserListResponse getUserList(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserListDto> content = new ArrayList<>();
        for(User user : users) {
            String courseName = "";

            Optional<CourseEntityUser> courseEntityUser = courseUserRepository.findCourseEntityUserByUserId(user.getId());
            courseEntityUser.ifPresent(result -> courseEntityUser.get());

            if(courseEntityUser.isPresent()) {
                Optional<CourseEntity> courseEntity =
                    courseRepository.findById(courseEntityUser.get().getCourseEntity().getId());
                if(courseEntity.isPresent()){
                    courseName = courseEntity.get().getName();
                }
            }
            content.add(UserListDto.of(user,courseName));
        }

        return new UserListResponse(content, pageable, users);
    }
}
